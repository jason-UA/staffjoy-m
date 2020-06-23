package com.example.auth.controller;

import com.example.account.client.AccountClient;
import com.example.account.dto.CreateAccountRequest;
import com.example.account.dto.GenericAccountResponse;
import com.example.common.api.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    AccountClient accountClient;

    @PostMapping("/register")
    public GenericAccountResponse register(@RequestBody @Valid CreateAccountRequest request) {
       return  accountClient.createAccount(request);
    }

    @GetMapping("/activate")
    public BaseResponse activate(@RequestParam(value="activate_token") String activateToken) {
        return accountClient.activateAccount(activateToken);
    }
}
