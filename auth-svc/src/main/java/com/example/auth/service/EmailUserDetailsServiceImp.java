package com.example.auth.service;

import com.example.account.dto.SecurityAccountDto;
import com.example.auth.component.email.service.EmailUserDetailsService;
import com.example.common.dto.DetailUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class EmailUserDetailsServiceImp implements EmailUserDetailsService {

    @Autowired
    SecurityAccountService accountService;

    @Override
    public String loadCodeByEmail(String email) {
        return "123456";
    }

    @Override
    public UserDetails loadUserByEmail(String email) {
        SecurityAccountDto accountDto = accountService.securityAccountByEmail(email);

        return new DetailUser(accountDto.getId(), accountDto.getEmail(), accountDto.getPassword(), accountDto.isEnabled(),
                accountDto.isAccountNonExpired(), accountDto.isCredentialsNonExpired(),
                accountDto.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList(accountDto.getRole().name()));
    }
}
