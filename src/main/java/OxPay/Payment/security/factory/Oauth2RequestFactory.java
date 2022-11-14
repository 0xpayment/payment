package OxPay.Payment.security.factory;

import OxPay.Payment.security.model.AuthUserDetails;
import com.google.common.collect.ImmutableSet;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Oauth2RequestFactory {
    private static final String CLIENT_ID = "0xPay-Web";
    private static final Set<String> AUTHORITIES = ImmutableSet.of("READ", "WRITE");

    public static OAuth2Request createOauth2Request(AuthUserDetails authUserDetails) {
        return new OAuth2Request(
                Collections.emptyMap(),
                CLIENT_ID,
                authUserDetails.getAuthorities(),
                true,
                AUTHORITIES,
                null,
                null,
                null,
                Collections.emptyMap());
    }
}
