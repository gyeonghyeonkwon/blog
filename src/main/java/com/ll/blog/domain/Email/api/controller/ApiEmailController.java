package com.ll.blog.domain.Email.api.controller;

import com.ll.blog.domain.Email.dto.EmailRequest;
import com.ll.blog.domain.Email.service.EmailService;
import groovy.util.logging.Log;
import groovy.util.logging.Slf4j;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@lombok.extern.slf4j.Slf4j

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class ApiEmailController {

    private final EmailService emailService;

    @PostMapping("/mailSend")
    public String mailSend(@RequestBody @Valid EmailRequest emailRequest) {
        log.info("이메일 인증 이메일: " + emailRequest.getEmail());
        return emailService.joinEmail(emailRequest.getEmail());
    }
}
