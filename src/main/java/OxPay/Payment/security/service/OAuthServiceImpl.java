package OxPay.Payment.security.service;

import OxPay.Payment.exception.BadRequestException;
import OxPay.Payment.model.entity.Merchant;
import OxPay.Payment.persistence.MerchantRepository;
import OxPay.Payment.security.factory.Oauth2RequestFactory;
import OxPay.Payment.security.model.AuthUserDetails;
import OxPay.Payment.security.model.Authority;
import OxPay.Payment.security.model.dto.SignupRequest;
import OxPay.Payment.security.model.entity.User;
import OxPay.Payment.security.repository.UserRepository;
import com.OxPay.uid.UidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

public class OAuthServiceImpl implements OAuthService {

    private final UserRepository userRepository;
    private final MerchantRepository merchantRepository;
    private final UidGenerator uidGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final AuthorizationServerTokenServices tokenServices;
    private final TokenStore tokenStore;

    @Autowired
    public OAuthServiceImpl(final UserRepository userRepository,
                            final MerchantRepository merchantRepository,
                            final UidGenerator uidGenerator,
                            final PasswordEncoder passwordEncoder,
                            final UserDetailsService userDetailsService,
                            final AuthorizationServerEndpointsConfiguration configuration,
                            final TokenStore tokenStore) {
        this.userRepository = userRepository;
        this.merchantRepository = merchantRepository;
        this.uidGenerator = uidGenerator;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.tokenServices = configuration.getEndpointsConfigurer().getTokenServices();
        this.tokenStore = tokenStore;
    }

    @Transactional
    @Override
    public OAuth2AccessToken saveUserAndCreateAccessToken(final SignupRequest request) {
        if (this.userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Account already exists for this email");
        }
        final var password = this.passwordEncoder.encode(request.getPassword());

        final var merchant = new Merchant();
        merchant.setId(this.uidGenerator.getUID());
        merchant.setName(request.getMerchantName());
        merchant.setAddress(request.getMerchantWalletAddress());
        merchant.setCreatedAt(Instant.now());
        merchant.setUpdatedAt(Instant.now());
        this.merchantRepository.save(merchant);

        final var user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(password);
        user.setState("ACTIVE");
        user.setAuthorities(Authority.ROLE_MASTER.name());
        user.setMerchantId(merchant.getId());
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        this.userRepository.save(user);

        return createAccessToken(user);
    }

    @Override
    public void revokeAccessToken(final String tokenValue) {
        final var accessToken = this.tokenStore.readAccessToken(tokenValue);
        this.tokenStore.removeAccessToken(accessToken);

        final var refreshToken = accessToken.getRefreshToken();
        this.tokenStore.removeRefreshToken(refreshToken);
    }

    private OAuth2AccessToken createAccessToken(final User user) {
        final var authUserDetails = (AuthUserDetails) this.userDetailsService.loadUserByUsername(user.getEmail());
        final var oAuth2Request = Oauth2RequestFactory.createOauth2Request(authUserDetails);
        final var authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authUserDetails.getUsername(), user.getPassword(), authUserDetails.getAuthorities());
        final var auth = new OAuth2Authentication(oAuth2Request, authenticationToken);
        return this.tokenServices.createAccessToken(auth);
    }
}
