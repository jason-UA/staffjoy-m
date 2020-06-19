package com.example.auth.service;

import com.example.account.client.AccountClient;
import com.example.account.dto.SecurityAccountDto;
import com.example.account.dto.SecurityAccountResponse;
import com.example.account.dto.SecurityRequest;
import com.example.common.dto.DetailUser;
import com.example.common.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    AccountClient accountClient;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityAccountResponse accountResponse = null;
        try {
            accountResponse = accountClient.securityAccount(SecurityRequest.builder().email(username).build());
        } catch (Exception ex) {
            String errMsg = "unable to get account";
            handleErrorAndThrowException(ex, errMsg);
        }
        if (!accountResponse.isSuccess()) {
            handleErrorAndThrowException(accountResponse.getMessage());
        }
        SecurityAccountDto accountDto = accountResponse.getAccount();

        return new DetailUser(accountDto.getId(), accountDto.getEmail(), accountDto.getPassword(), accountDto.isEnabled(),
                accountDto.isAccountNonExpired(), accountDto.isCredentialsNonExpired(),
                accountDto.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList(accountDto.getRole().name()));
    }

    void handleErrorAndThrowException(String errMsg) {
        log.error(errMsg);
        throw new ServiceException(errMsg);
    }

    void handleErrorAndThrowException(Exception ex, String errMsg) {
        log.error(errMsg, ex);
        throw new ServiceException(errMsg, ex);
    }
}
