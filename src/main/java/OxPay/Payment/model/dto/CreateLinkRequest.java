package OxPay.Payment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class CreateLinkRequest {
    private String description;
    private Optional<String> name;
    private Optional<String> amount;
    private Optional<String> currency;
    private Optional<Boolean> requireCustomerName;
    private Optional<Boolean> requireCustomerEmail;
}
