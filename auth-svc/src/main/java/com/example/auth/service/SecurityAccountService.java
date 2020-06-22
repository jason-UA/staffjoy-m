package com.example.auth.service;

import com.example.account.client.AccountClient;
import com.example.account.dto.*;
import com.example.common.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SecurityAccountService {

    @Autowired
    AccountClient accountClient;

    public SecurityAccountDto securityAccountByEmail(String email) {
        SecurityAccountResponse accountResponse = null;
        try {
            accountResponse = accountClient.securityAccount(SecurityRequest.builder().email(email).build());
        } catch (Exception ex) {
            String errMsg = "unable to get account";
            handleErrorAndThrowException(ex, errMsg);
        }
        if (!accountResponse.isSuccess()) {
            handleErrorAndThrowException(accountResponse.getMessage());
        }
        SecurityAccountDto accountDto = accountResponse.getAccount();
        return accountDto;
    }

    public GenericAccountResponse createAccount(CreateAccountRequest request) {
        return accountClient.createAccount(request);
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
