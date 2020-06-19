package com.example.account.service;


import com.example.account.dto.AccountDto;
import com.example.account.dto.SecurityAccountDto;
import com.example.account.dto.UserRole;
import com.example.account.model.Account;
import com.example.account.repository.AccountRepository;
import com.example.common.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public AccountDto getOrCreate(String name, String email, String phoneNumber, String password) {
        Account existingAccount = null;
        if (StringUtils.hasText(email)) {
            existingAccount = accountRepository.findAccountByEmail(email);
        }
        if (existingAccount == null && StringUtils.hasText(phoneNumber)) {
            existingAccount = accountRepository.findAccountByPhoneNumber(phoneNumber);
        }

        if (existingAccount != null) {
            return this.convertToDto(existingAccount);
        }
        return this.create(name, email, phoneNumber, password);
    }

    public AccountDto create(String name, String email, String phoneNumber, String password) {
        if (StringUtils.hasText(email)) {
            Account foundAccount = accountRepository.findAccountByEmail(email);
            if (foundAccount != null) {
                throw new ServiceException("A user with that email already exists. Try a password reset");
            }
        } else {
            throw new ServiceException("email can't empty !");
        }

        if (StringUtils.hasText(phoneNumber)) {
            Account foundAccount = accountRepository.findAccountByPhoneNumber(phoneNumber);
            if (foundAccount != null) {
                throw new ServiceException("A user with that phonenumber already exists. Try a password reset");
            }
        }

        // Column name/email/phone_number cannot be null
        if (name == null) {
            name = "";
        }
        if (email == null) {
            email = "";
        }
        if (phoneNumber == null) {
            phoneNumber = "";
        }

        Account account = Account.builder()
                .email(email)
                .username(name)
                .phoneNumber(phoneNumber)
                .password(passwordEncoder.encode(password))
                .role(UserRole.user)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        try {
            accountRepository.save(account);
        } catch (Exception ex) {
            String errMsg = "Could not create user account";
            throw new ServiceException(errMsg, ex);
        }
        log.info("create account", account);
        AccountDto accountDto = this.convertToDto(account);
        return accountDto;
    }

    public SecurityAccountDto securityAccountByEmail(String email) {
        Account account = accountRepository.findAccountByEmail(email);
        return convertToDeo(account);
    }

    private AccountDto convertToDto(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }

    private SecurityAccountDto convertToDeo(Account account) {
        return modelMapper.map(account, SecurityAccountDto.class);
    }
}
