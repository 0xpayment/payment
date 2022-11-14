package OxPay.Payment.security.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.StringUtils;

@Value
@Builder(toBuilder = true)
public class Oauth2AccessTokenResponse {
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("token_type")
    String tokenType;
    @JsonProperty("refresh_token")
    String refreshToken;
    @JsonProperty("expires_in")
    Integer expiresIn;
    @JsonProperty("scope")
    String scope;

    public static Oauth2AccessTokenResponse from(OAuth2AccessToken token) {
        return Oauth2AccessTokenResponse.builder()
                .accessToken(token.getValue())
                .tokenType(token.getTokenType())
                .refreshToken(token.getRefreshToken().getValue())
                .expiresIn(token.getExpiresIn())
                .scope(StringUtils.collectionToDelimitedString(token.getScope(), " "))
                .build();
    }
}
