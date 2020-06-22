package com.example.account.client;

import com.example.account.AccountConstant;
import com.example.account.dto.CreateAccountRequest;
import com.example.account.dto.GenericAccountResponse;
import com.example.account.dto.SecurityAccountResponse;
import com.example.account.dto.SecurityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = AccountConstant.SERVICE_NAME, path = "/v1/account", url = "${staffjoy.account-service-endpoint}")


public interface AccountClient {
    @PostMapping("/create")
    public GenericAccountResponse createAccount(@RequestBody @Valid CreateAccountRequest request);

    @PostMapping("/security_account")
    public SecurityAccountResponse securityAccount(@RequestBody @Valid SecurityRequest request);
}
