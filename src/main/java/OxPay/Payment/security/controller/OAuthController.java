package OxPay.Payment.security.controller;

import OxPay.Payment.security.model.dto.LoginRequest;
import OxPay.Payment.security.model.dto.SignupRequest;
import OxPay.Payment.security.model.dto.Oauth2AccessTokenResponse;
import OxPay.Payment.security.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService OAuthService;

    @PostMapping("/signup")
    public Oauth2AccessTokenResponse singUp(@RequestBody final SignupRequest request) {
        final var accessToken = this.OAuthService.saveUserAndCreateAccessToken(request);
        return Oauth2AccessTokenResponse.from(accessToken);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") final String authorization) {
        final var tokenValue = authorization.replace("Bearer", "").trim();
        this.OAuthService.revokeAccessToken(tokenValue);
    }
}
