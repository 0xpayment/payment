package OxPay.Payment.factory;

import OxPay.Payment.model.domain.OrderDo;
import OxPay.Payment.model.dto.SubmitOrderRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderFactory {
    public static OrderDo submitOrder(Long orderId, SubmitOrderRequest request) {
        return OrderDo.builder()
                .id(orderId)
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .build();
    }
}
