package com.example.account.controller;


import com.example.account.dto.*;
import com.example.account.service.AccountService;
import com.example.common.api.BaseResponse;
import com.example.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public GenericAccountResponse createAccount(@RequestBody @Valid CreateAccountRequest request) {
        AccountDto accountDto = accountService.create(request.getName(), request.getEmail(), request.getPhoneNumber(), request.getPassword());
        GenericAccountResponse response = new GenericAccountResponse(accountDto);
        return response;
    }

    @PostMapping("/security_account")
    public SecurityAccountResponse securityAccount(@RequestBody @Valid SecurityRequest request) {
        SecurityAccountDto accountDto = accountService.securityAccountByEmail(request.getEmail());
        SecurityAccountResponse response = new SecurityAccountResponse(accountDto);
        return response;
    }

    @GetMapping("/activate")
    public BaseResponse activateAccount(@RequestParam(value="activate_token") String activateToken) {
        accountService.activateAccount(activateToken);
        return new BaseResponse("activate account successful!", ResultCode.SUCCESS);
    }



}
