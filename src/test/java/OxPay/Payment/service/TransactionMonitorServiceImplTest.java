package OxPay.Payment.service;

import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

@ExtendWith(SpringExtension.class)
public class TransactionMonitorServiceImplTest {

    private final OrderService orderService = Mockito.mock(OrderService.class);
    private final TransactionMonitorService monitorService = Mockito.spy(new TransactionMonitorServiceImpl(orderService, "0xEC8cD0f941a5c3Cc522ef50e88682070333BeC72", "wss://ws-matic-mumbai.chainstacklabs.com"));

    @Test
    public void testMonitorCanConnectToMumbaiTestnet() throws InterruptedException, ExecutionException, IOException {
            this.monitorService.webSocketPull();
    }

    @Test
    @Ignore
    public void testGetOrderIdFromInputData() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String inputData = "0x84a60232000000000000000000000000fe4f5145f6e09952a5ba9e956ed0c25e3fa4c7f100000000000000000000000090cf09a1155fe543013550edc96a14f7ff404293000000000000000000000000000000000000000000000000000000003b9aca000000000000000000000000000000000000000000000000000000000000000080000000000000000000000000000000000000000000000000000000000000002c3078643138346431646233616431636132326164623665346565333364663337663434653534363066323b310000000000000000000000000000000000000000";
        final var transactionServiceImpl = new TransactionMonitorServiceImpl(orderService, "0xEC8cD0f941a5c3Cc522ef50e88682070333BeC72", "wss://ws-matic-mumbai.chainstacklabs.com");

        final var orderId = transactionServiceImpl.getOrderLinkIdFromInputData(inputData);
        Assertions.assertEquals("0xd184d1db3ad1ca22adb6e4ee33df37f44e5460f22124049464619434000", orderId);
    }
}
