package OxPay.Payment.model.domain;

import OxPay.Payment.model.Currency;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class LinkDo {
    private Long id;
    private Long merchantId;
    private String description;
    @Nullable
    private String name;
    @Nullable
    private BigDecimal amount;
    private Currency currency;
    private Boolean requireCustomerName;
    private Boolean requireCustomerEmail;
}
