package OxPay.Payment.configuration;

import OxPay.Payment.persistence.LinkRepository;
import OxPay.Payment.persistence.MerchantRepository;
import OxPay.Payment.persistence.OrderRepository;
import OxPay.Payment.service.*;
import com.OxPay.uid.UidGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ServiceConfiguration {

    @Bean
    public TransactionMonitorService transactionMonitorService(final OrderService orderService,
                                                               @Value("${order.monitor.contract.address}") final String monitorContractAddress,
                                                               @Value("${order.websocket.uri}") final String websocketUri) {
        return new TransactionMonitorServiceImpl(orderService, monitorContractAddress, websocketUri);
    }

    @Bean
    public LinkService linkService(final UidGenerator uidGenerator, final LinkRepository linkRepository) {
        return new LinkServiceImpl(uidGenerator, linkRepository);
    }

    @Bean
    public OrderService orderService(final OrderRepository orderRepository, final LinkService linkService, final UidGenerator uidGenerator, final MerchantRepository merchantRepository) {
        return new OrderServiceImpl(orderRepository, linkService, merchantRepository, uidGenerator);
    }
}
