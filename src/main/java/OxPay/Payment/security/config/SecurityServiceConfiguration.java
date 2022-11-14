package OxPay.Payment.security.config;

import OxPay.Payment.persistence.MerchantRepository;
import OxPay.Payment.security.repository.UserRepository;
import OxPay.Payment.security.service.OAuthService;
import OxPay.Payment.security.service.OAuthServiceImpl;
import OxPay.Payment.security.service.UserDetailsServiceImpl;
import com.OxPay.uid.UidGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class SecurityServiceConfiguration {
    @Bean
    public UserDetailsService userDetailsService(final UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public OAuthService authService(final UserRepository userRepository,
                                    final MerchantRepository merchantRepository,
                                    final UidGenerator uidGenerator,
                                    final PasswordEncoder passwordEncoder,
                                    final UserDetailsService userDetailsService,
                                    final AuthorizationServerEndpointsConfiguration configuration,
                                    final TokenStore tokenStore) {
        return new OAuthServiceImpl(userRepository, merchantRepository, uidGenerator, passwordEncoder, userDetailsService, configuration, tokenStore);
    }
}
