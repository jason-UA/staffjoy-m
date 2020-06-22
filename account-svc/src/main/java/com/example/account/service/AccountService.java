package com.example.account.service;


import com.example.account.AccountConstant;
import com.example.account.crypto.Sign;
import com.example.account.dto.AccountDto;
import com.example.account.dto.GenericAccountResponse;
import com.example.account.dto.SecurityAccountDto;
import com.example.account.dto.UserRole;
import com.example.account.model.Account;
import com.example.account.props.AppProps;
import com.example.account.repository.AccountRepository;
import com.example.common.api.BaseResponse;
import com.example.common.env.EnvConfig;
import com.example.common.error.ServiceException;
import com.example.mail.client.MailClient;
import com.example.mail.dto.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private AppProps appProps;

    @Autowired
    private EnvConfig envConfig;

    @Autowired
    private MailClient mailClient;

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
                .enabled(false)
                .build();

        try {
            accountRepository.save(account);
        } catch (Exception ex) {
            String errMsg = "Could not create user account";
            throw new ServiceException(errMsg, ex);
        }
        if (StringUtils.hasText(email)) {
            String emailName = name;
            if (StringUtils.isEmpty(emailName)) {
                emailName = "there";
            }
            String subject = "Activate your staffjoy account";
            sendEmail(account.getId(), email, emailName, subject, AccountConstant.ACTIVATE_ACCOUNT_TMPL, true);
        }

        log.info("create account", account);
        AccountDto accountDto = this.convertToDto(account);
        return accountDto;
    }

    public SecurityAccountDto securityAccountByEmail(String email) {
        Account account = accountRepository.findAccountByEmail(email);
        return convertToDeo(account);
    }

    public GenericAccountResponse activateAccount(String token) {
        Map<String, String> map = Sign.verifyEmailConfirmationToken(token, appProps.getSigningSecret());
        String userId = map.get("user_id");
        String email = map.get("email");
//        Account account = accountRepository.findAccountById(Long.valueOf(userId).longValue());
//        AccountDto accountDto = convertToDto(account);

        return null;
    }

    private void sendEmail(Long userId, String email, String name, String subject, String template, boolean activateOrConfirm) {
        String token = null;
        try {
            token = Sign.generateEmailConfirmationToken(userId, email, appProps.getSigningSecret());
        } catch (Exception ex) {
            String errMsg = "Could not create token";
            throw new ServiceException(errMsg, ex);
        }

        String pathFormat = "/auth/activate/%s";
        if (!activateOrConfirm) {
            pathFormat = "/auth/reset/%s";
        }
        String path = String.format(pathFormat, token);
        URI link = null;
        try {
            link = new URI(envConfig.getScheme(), envConfig.getExternalApex(), path, null);
        } catch (URISyntaxException ex) {
            String errMsg = "Could not create activation url";
            if (!activateOrConfirm) {
                errMsg = "Could not create reset url";
            }
            throw new ServiceException(errMsg, ex);
        }

        String htmlBody = null;
        if (activateOrConfirm) {
            htmlBody = String.format(template, name, link.toString(), link.toString(), link.toString());
        } else { //rest
            htmlBody = String.format(template, link.toString(), link.toString());
        }

        EmailRequest emailRequest = EmailRequest.builder()
                .to(email)
                .name(name)
                .subject(subject)
                .htmlBody(htmlBody)
                .build();

        BaseResponse baseResponse = null;
        try {
            baseResponse = mailClient.send(emailRequest);
        } catch (Exception ex) {
            String errMsg = "Unable to send email";
            throw new ServiceException(errMsg, ex);
        }
        if (!baseResponse.isSuccess()) {
            throw new ServiceException(baseResponse.getMessage());
        }
    }

    private AccountDto convertToDto(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }

    private SecurityAccountDto convertToDeo(Account account) {
        return modelMapper.map(account, SecurityAccountDto.class);
    }
}
