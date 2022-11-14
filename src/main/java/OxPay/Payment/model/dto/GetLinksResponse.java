package OxPay.Payment.model.dto;

import OxPay.Payment.model.entity.Link;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetLinksResponse {
    private List<Link> links;
}
