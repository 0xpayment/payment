package OxPay.Payment.service;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.web3j.abi.TypeDecoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.websocket.WebSocketService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TransactionMonitorServiceImpl implements TransactionMonitorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionMonitorService.class);
    private final OrderService orderService;
    private final String monitorContractAddress;
    private final String webSocketUri;

    public TransactionMonitorServiceImpl(final OrderService orderService, final String monitorContractAddress, final String webSocketUri) {
        this.orderService = orderService;
        this.monitorContractAddress = monitorContractAddress;
        this.webSocketUri = webSocketUri;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void webSocketPull() throws IOException {
        LOGGER.info("Start pulling transactions of {} through websocket address {} ...", monitorContractAddress, webSocketUri);

        // initialize web socket service
        WebSocketService wss = new WebSocketService(this.webSocketUri, false);
        try {
            wss.connect();
        } catch (final Exception e) {
            LOGGER.error("Error while connecting to WSS service: ", e);
            throw e;
        }

        // build web3j client
        Web3j web3j = Web3j.build(wss);

        // create filter for contract events, change EARLIEST -> LATEST for only get new events.
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, this.monitorContractAddress);

        // subscribe to events
        web3j.ethLogFlowable(filter).subscribe(event -> {
            LOGGER.info("Event received: {}", event.toString());
            // Getting transaction based on transaction hash from the event log.
            EthTransaction txn = web3j.ethGetTransactionByHash(event.getTransactionHash()).send();
            LOGGER.info("*** transaction hash is: {}", event.getTransactionHash());
            LOGGER.info("*** transaction input data is: {}", txn.getResult().getInput());
            final var orderLinkId = this.getOrderLinkIdFromInputData(txn.getResult().getInput());
            this.orderService.updateOrderStatusByOrderLinkId(orderLinkId);
        }, error -> {
            LOGGER.error("Error: " + error);
        });
    }

    /**
     * Use this ABI encode doc for reference: https://docs.soliditylang.org/en/develop/abi-spec.html
     *
     * @param inputData Hex string
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @VisibleForTesting
    String getOrderLinkIdFromInputData(final String inputData) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LOGGER.info("InputData is {}", inputData);

        Method refMethod = TypeDecoder.class.getDeclaredMethod("decode", String.class, int.class, Class.class);
        refMethod.setAccessible(true);

        LOGGER.info("Method signature: {}", inputData.substring(0,10));

        final var to = inputData.substring(10,74);
        final var toAddress = (Address) refMethod.invoke(null,to,0, Address.class);
        LOGGER.info("To address: {}", toAddress.toString());

        final var from = inputData.substring(74, 138);
        final var fromAddress = (Address) refMethod.invoke(null,from,0, Address.class);
        LOGGER.info("From address: {}", fromAddress.toString());

        final var value = inputData.substring(138, 202);
        final var amount = (Uint256) refMethod.invoke(null,value,0, Uint256.class);
        LOGGER.info("Transaction Amount: {}", amount.getValue());

        final var data = inputData.substring(202);
        final var orderLinkId = (Utf8String) refMethod.invoke(null,data,64, Utf8String.class);
        LOGGER.info("merchantAddress and OrderId: {}", orderLinkId.getValue());

        return orderLinkId.getValue();
    }
}
