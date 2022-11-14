package OxPay.Payment.model.dto;

import OxPay.Payment.model.entity.Link;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TestGetLinksResponse {

    private TestGetLinksResponseData data;

    @Data
    @AllArgsConstructor
    public static class TestGetLinksResponseData {
        private Integer total;
        private List<Link> items;
    }
}
