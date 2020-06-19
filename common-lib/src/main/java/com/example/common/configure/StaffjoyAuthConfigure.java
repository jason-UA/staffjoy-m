package com.example.common.configure;

import com.example.common.handler.StaffjoyAccessDeniedHandler;
import com.example.common.handler.StaffjoyAuthExceptionEntryPoint;
import com.example.common.jwt.CustomAccessTokenEnhancer;
import com.example.common.jwt.CustomerJwtAccessTokenConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class StaffjoyAuthConfigure {

    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public StaffjoyAccessDeniedHandler accessDeniedHandler() {
        return new StaffjoyAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public StaffjoyAuthExceptionEntryPoint authenticationEntryPoint() {
        return new StaffjoyAuthExceptionEntryPoint();
    }


    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        CustomerJwtAccessTokenConverter accessTokenConverter = new CustomerJwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("staffjoy");
        return accessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomAccessTokenEnhancer();
    }


}
