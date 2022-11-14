package OxPay.Payment.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {
    @Id
    private Long id;
    private String name;
    private String address;
    private Instant createdAt;
    private Instant updatedAt;
}
