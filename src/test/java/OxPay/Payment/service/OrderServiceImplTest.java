package OxPay.Payment.service;

import OxPay.Payment.enums.OrderState;
import OxPay.Payment.model.Currency;
import OxPay.Payment.model.entity.Order;
import OxPay.Payment.persistence.MerchantRepository;
import OxPay.Payment.persistence.OrderRepository;
import com.OxPay.uid.UidGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class OrderServiceImplTest {

    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private final LinkService linkService = Mockito.mock(LinkService.class);
    private final UidGenerator uidGenerator = Mockito.mock(UidGenerator.class);
    private final MerchantRepository merchantRepository = Mockito.mock(MerchantRepository.class);
    private final OrderServiceImpl orderServiceImpl = new OrderServiceImpl(this.orderRepository, this.linkService, this.merchantRepository, this.uidGenerator);

    @Test
    public void updatePaymentWithValidLink_shouldUpdatePaymentToCompleted() {
        final var fakeOrder = new Order(2124049464619434000L, 1L, 1L, BigDecimal.valueOf(0.00002), Currency.USD, "name", "email@email.com", "", OrderState.CREATED, Instant.now(), Instant.now());
        Mockito.when(this.orderRepository.findById(2124049464619434000L))
                .thenReturn(Optional.of(fakeOrder));

        Mockito.when(this.orderRepository.save(fakeOrder)).thenReturn(fakeOrder);

        final var orderLinkId = "0xd184d1db3ad1ca22adb6e4ee33df37f44e5460f22124049464619434000";
        this.orderServiceImpl.updateOrderStatusByOrderLinkId(orderLinkId);
    }
}
