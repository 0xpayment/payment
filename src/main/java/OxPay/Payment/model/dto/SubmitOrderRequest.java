package OxPay.Payment.model.dto;

import lombok.Value;

@Value
public class SubmitOrderRequest {
    String customerName;
    String customerEmail;
}
