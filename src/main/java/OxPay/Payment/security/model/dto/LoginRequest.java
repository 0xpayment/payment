package OxPay.Payment.security.model.dto;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Value
public class LoginRequest {
    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Please provide a valid email address")
    String email;

    @NotEmpty(message = "Password cannot be empty")
    String password;
}
