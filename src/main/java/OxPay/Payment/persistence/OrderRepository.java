package OxPay.Payment.persistence;

import OxPay.Payment.enums.OrderState;
import OxPay.Payment.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByLinkIdAndState(final long linkId, final OrderState state);
    List<Order> findByMerchantIdAndState(final long merchantId, final OrderState state);
    List<Order> findByMerchantId(final long merchantId);
}
