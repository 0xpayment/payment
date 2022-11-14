package OxPay.Payment.model.domain;

import OxPay.Payment.enums.OrderState;
import OxPay.Payment.model.Currency;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderDo {
    private Long id;
    private Long linkId;
    private Long merchantId;
    private BigDecimal amount;
    private Currency currency;
    private String customerName;
    private String customerEmail;
    private OrderState state;
}
