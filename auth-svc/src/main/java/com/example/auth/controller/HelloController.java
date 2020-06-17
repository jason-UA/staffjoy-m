package com.example.auth.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        return "hello! this is auth-svc";
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

}
