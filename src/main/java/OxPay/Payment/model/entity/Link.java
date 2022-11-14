package OxPay.Payment.model.entity;

import OxPay.Payment.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    @Id
    private Long id;
    private Long merchantId;
    private String name;
    private String description;
    private BigDecimal amount;
    private Currency currency;
    private Boolean requireCustomerName;
    private Boolean requireCustomerEmail;
    private Instant createdAt;
    private Instant updatedAt;
}
