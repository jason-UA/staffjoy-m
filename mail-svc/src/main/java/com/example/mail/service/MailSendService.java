package com.example.mail.service;


import com.example.mail.dto.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;

@Service
@Slf4j
public class MailSendService {

    public void SendMailAsync(EmailRequest request) {
        log.info(request.toString());
    }
}
