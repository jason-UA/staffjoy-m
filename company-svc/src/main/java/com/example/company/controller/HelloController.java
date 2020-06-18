package com.example.company.controller;

import com.example.common.auth.AuthContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        log.info("hello");
        return "hello! this is company-svc userID:" + AuthContext.getUserId();
    }
}
