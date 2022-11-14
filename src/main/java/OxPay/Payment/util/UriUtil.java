package OxPay.Payment.util;

import lombok.experimental.UtilityClass;

import java.net.URI;

@UtilityClass
public class UriUtil {
//    private static final String ORDER_SERVER_BASE_URL = "http://localhost:9090/orders"; // TODO: inject base url
    private static final String ORDER_SERVER_BASE_URL = "http://localhost:3000/order"; // TODO: inject base url

    public URI createGetOrderByIDUri(Long orderId) {
        return URI.create(String.join("/", ORDER_SERVER_BASE_URL, String.valueOf(orderId)));
    }
}
