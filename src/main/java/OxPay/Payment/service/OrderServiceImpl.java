package OxPay.Payment.service;

import OxPay.Payment.enums.OrderState;
import OxPay.Payment.exception.BadRequestException;
import OxPay.Payment.exception.NotFoundException;
import OxPay.Payment.model.domain.OrderDo;
import OxPay.Payment.model.entity.Order;
import OxPay.Payment.persistence.MerchantRepository;
import OxPay.Payment.persistence.OrderRepository;
import com.OxPay.uid.UidGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final LinkService linkService;
    private final MerchantRepository merchantRepository;
    private final UidGenerator uidGenerator;

    @Override
    public Long crateOrderForLink(final Long linkId) {
        final var link = this.linkService.getLinkById(linkId);
        final var merchant = this.merchantRepository.findById(link.getMerchantId()).orElseThrow(() -> new NotFoundException("cannot find merchant for the link"));
        final var order = new Order();
        final var id = this.uidGenerator.getUID();
        order.setId(id);
        order.setLinkId(linkId);
        order.setMerchantId(link.getMerchantId());
        order.setAmount(link.getAmount());
        order.setCurrency(link.getCurrency());
        order.setCustomerName(null);
        order.setCustomerEmail(null);
        order.setState(OrderState.CREATED);
        order.setOrderMeta(merchant.getAddress() + ";" + id);
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());

        this.orderRepository.save(order);

        return order.getId();
    }


    public Order submitOrder(final OrderDo orderDo) {
        final var order = this.getOrderById(orderDo.getId());
        final var link = this.linkService.getLinkById(order.getLinkId());

        if ((link.getRequireCustomerName() ^ Objects.nonNull(orderDo.getCustomerName()))) {
            throw new BadRequestException("customer name violates the link constraint");
        }
        if ((link.getRequireCustomerEmail() ^ Objects.nonNull(orderDo.getCustomerEmail()))) {
            throw new BadRequestException("customer email violates the link constraint");
        }

        order.setCustomerName(orderDo.getCustomerName());
        order.setCustomerEmail(orderDo.getCustomerEmail());

        this.orderRepository.save(order);

        return order;
    }

    @Override
    public List<Order> getOrdersByLinkId(final Long linkId) {
        return this.orderRepository.findByLinkIdAndState(linkId, OrderState.COMPLETED);
    }

    @Override
    public Order getOrderById(final Long orderId) {
        return this.orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("could not find the order with id: " + orderId));
    }

    @Override
    public void updateOrderStatusByOrderLinkId(final String orderLinkId) {
        LOGGER.info("Try to update order state for order: {}", orderLinkId);

        final var ids = orderLinkId.split(";");
        if (ids.length < 2) {
            LOGGER.info("invalid order link id: {}", orderLinkId);
            return;
        }

        final var orderId = Long.valueOf(ids[1]);
        final var orderOptional = this.orderRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            LOGGER.info("Update order state to COMPLETE for order: {}", orderId);
            final var order = orderOptional.get();
            order.setState(OrderState.COMPLETED);
            this.orderRepository.save(order);
        } else {
            LOGGER.info("Order with id {} does not exists, skip processing...", orderId);
        }
    }

    public List<Order> getMerchantOrderByStatus(final Long merchantId, final OrderState state) {
        if (null == state) {
            return this.orderRepository.findByMerchantId(merchantId);
        }

        return this.orderRepository.findByMerchantIdAndState(merchantId, state);
    }
}
