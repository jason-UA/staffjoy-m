package com.example.auth.configure;

import com.example.auth.component.email.service.EmailUserDetailsService;
import com.example.auth.handler.BaseAuthenticationFailureHandler;
import com.example.auth.handler.BaseAuthenticationSuccessHandler;
import com.example.auth.handler.BaseLogoutSuccessHandler;
import com.example.common.configure.StaffjoyAuthRestConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Import(StaffjoyAuthRestConfig.class)
@Configuration
public class AppConfig {

    @Bean
    public BaseAuthenticationFailureHandler baseAuthenticationFailureHandler() {
        BaseAuthenticationFailureHandler baseAuthenticationFailureHandler = new BaseAuthenticationFailureHandler();
        baseAuthenticationFailureHandler.setCodeParam("");
        baseAuthenticationFailureHandler.setMsgParam("");
        return baseAuthenticationFailureHandler;
    }

    @Bean
    public BaseAuthenticationSuccessHandler baseAuthenticationSuccessHandler(ClientDetailsService clientDetailsService,
                                                                             AuthorizationServerTokenServices authorizationServerTokenServices,
                                                                             PasswordEncoder passwordEncoder) {
        BaseAuthenticationSuccessHandler baseAuthenticationSuccessHandler = new BaseAuthenticationSuccessHandler()
                .clientDetailsService(clientDetailsService)
                .authorizationServerTokenServices(authorizationServerTokenServices)
                .passwordEncoder(passwordEncoder);
        return baseAuthenticationSuccessHandler;
    }

    @Bean
    public BaseLogoutSuccessHandler baseLogoutSuccessHandler(TokenStore tokenStore) {
        return new BaseLogoutSuccessHandler().tokenStore(tokenStore);
    }

    @Bean
    EmailSecurityConfigurerAdapter emailSecurityConfigurerAdapter(EmailUserDetailsService emailUserDetailsService,
                                                                  BaseAuthenticationFailureHandler baseAuthenticationFailureHandler,
                                                                  BaseAuthenticationSuccessHandler baseAuthenticationSuccessHandler) {
        return new EmailSecurityConfigurerAdapter()
                .baseAuthenticationFailureHandler(baseAuthenticationFailureHandler)
                .baseAuthenticationSuccessHandler(baseAuthenticationSuccessHandler)
                .emailUserDetailsService(emailUserDetailsService);
    }
}
