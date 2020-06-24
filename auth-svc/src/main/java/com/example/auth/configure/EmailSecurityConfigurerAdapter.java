package com.example.auth.configure;

import com.example.auth.component.email.EmailAuthenticationFilter;
import com.example.auth.component.email.EmailAuthenticationProvider;
import com.example.auth.component.email.service.EmailUserDetailsService;
import com.example.auth.handler.BaseAuthenticationFailureHandler;
import com.example.auth.handler.BaseAuthenticationSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 邮箱配置类
 *
 * @author tangchen
 */

public class EmailSecurityConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    private EmailUserDetailsService emailUserDetailsService;

    private BaseAuthenticationFailureHandler baseAuthenticationFailureHandler;

    private BaseAuthenticationSuccessHandler baseAuthenticationSuccessHandler;

    public EmailSecurityConfigurerAdapter baseAuthenticationFailureHandler(BaseAuthenticationFailureHandler baseAuthenticationFailureHandler) {
        this.baseAuthenticationFailureHandler = baseAuthenticationFailureHandler;
        return this;
    }

    public EmailSecurityConfigurerAdapter baseAuthenticationSuccessHandler(BaseAuthenticationSuccessHandler baseAuthenticationSuccessHandler) {
        this.baseAuthenticationSuccessHandler = baseAuthenticationSuccessHandler;
        return this;
    }

    public EmailSecurityConfigurerAdapter emailUserDetailsService(EmailUserDetailsService emailUserDetailsService) {
        this.emailUserDetailsService = emailUserDetailsService;
        return this;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {


        EmailAuthenticationFilter authenticationFilter = new EmailAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationSuccessHandler(baseAuthenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(baseAuthenticationFailureHandler);

        EmailAuthenticationProvider authenticationProvider = new EmailAuthenticationProvider();
        authenticationProvider.emailUserDetailsService(emailUserDetailsService);
        http.authenticationProvider(authenticationProvider)
                .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}