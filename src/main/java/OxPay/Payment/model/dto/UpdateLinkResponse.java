package OxPay.Payment.model.dto;

import OxPay.Payment.model.entity.Link;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateLinkResponse {
    private Link link;
}
