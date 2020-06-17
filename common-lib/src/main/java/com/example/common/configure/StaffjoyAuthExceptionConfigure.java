package com.example.common.configure;

import com.example.common.handler.StaffjoyAccessDeniedHandler;
import com.example.common.handler.StaffjoyAuthExceptionEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StaffjoyAuthExceptionConfigure {

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
}
