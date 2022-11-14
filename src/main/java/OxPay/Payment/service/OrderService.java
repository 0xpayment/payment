package OxPay.Payment.service;

import OxPay.Payment.enums.OrderState;
import OxPay.Payment.model.domain.OrderDo;
import OxPay.Payment.model.entity.Order;

import java.util.List;

public interface OrderService {
    Long crateOrderForLink(final Long linkId);

    Order getOrderById(final Long orderId);

    Order submitOrder(final OrderDo orderDo);

    List<Order> getOrdersByLinkId(final Long linkId);

    void updateOrderStatusByOrderLinkId(final String orderLinkId);

    List<Order> getMerchantOrderByStatus(final Long merchantId, final OrderState state);
}
