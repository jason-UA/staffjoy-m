package com.example.auth.controller;

import com.example.account.client.AccountClient;
import com.example.account.dto.CreateAccountRequest;
import com.example.account.dto.GenericAccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    AccountClient accountClient;

    @PostMapping("/register")
    public GenericAccountResponse register(@RequestBody @Valid CreateAccountRequest request) {
       return  accountClient.CreateAccount(request);
    }
}
