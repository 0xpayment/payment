package OxPay.Payment.model.dto;

import OxPay.Payment.model.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetLinkOrdersResponse {

    private GetLinkOrdersResponseData data;

    @Data
    @AllArgsConstructor
    public static class GetLinkOrdersResponseData {
        private Integer total;
        private List<Order> items;
    }
}
