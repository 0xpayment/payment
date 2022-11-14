package OxPay.Payment.security.config;

import OxPay.Payment.security.model.Authority;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "payment";
    private static final String SECURED_PATTERN = "/**";

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().disable()
                .authorizeRequests()
                .antMatchers("/oauth/signup").permitAll()
                .antMatchers("/link/**").permitAll()
                .antMatchers("/orders/**").permitAll()
                .and()
                .requestMatchers().antMatchers(SECURED_PATTERN)
                .and()
                .authorizeRequests().anyRequest().hasAnyRole(Authority.ROLE_ADMIN.value(), Authority.ROLE_MASTER.value());
    }
}

