package OxPay.Payment.security.model;

import OxPay.Payment.security.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class AuthUserDetails implements UserDetails {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthUserDetails.class);

    private static final long serialVersionUID = -7568522007172192358L;

    private Long id;
    private Long merchantId;
    private String email;
    private String password;
    private String state;
    private String authorities;

    public AuthUserDetails(final User user) {
        LOGGER.info("AuthUserDetails Constructor, user id {} , merchant id {}", user.getId(), user.getMerchantId());
        this.id = user.getId();
        this.merchantId = user.getMerchantId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.state = user.getState();
        this.authorities = user.getAuthorities();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Authority.getSetFromString(this.authorities)
                .stream().map(a -> new SimpleGrantedAuthority(a.toString()))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return this.id;
    }

    public Long getMerchantId() {
        return this.merchantId;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
