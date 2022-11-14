package OxPay.Payment.security.service;

import OxPay.Payment.security.model.dto.LoginRequest;
import OxPay.Payment.security.model.dto.SignupRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public interface OAuthService {
    OAuth2AccessToken saveUserAndCreateAccessToken(final SignupRequest request);
    void revokeAccessToken(final String tokenValue);
}
