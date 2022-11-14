package OxPay.Payment.model.entity;

import OxPay.Payment.enums.OrderState;
import OxPay.Payment.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "link_order")
public class Order {
    @Id
    private Long id;
    private Long linkId;
    private Long merchantId;
    private BigDecimal amount;
    private Currency currency;
    private String customerName;
    private String customerEmail;
    private String orderMeta;
    @Enumerated(EnumType.STRING)
    private OrderState state;
    private Instant createdAt;
    private Instant updatedAt;
}
