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
    SecurityAccountService accountService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityAccountDto accountDto = accountService.securityAccountByEmail(username);

        return new DetailUser(accountDto.getId(), accountDto.getEmail(), accountDto.getPassword(), accountDto.isEnabled(),
                accountDto.isAccountNonExpired(), accountDto.isCredentialsNonExpired(),
                accountDto.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList(accountDto.getRole().name()));
    }

}
