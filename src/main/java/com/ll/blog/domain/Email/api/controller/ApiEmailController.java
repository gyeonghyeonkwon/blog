package com.ll.blog.domain.Email.api.controller;

import com.ll.blog.domain.Email.dto.EmailRequest;
import com.ll.blog.domain.Email.service.EmailService;
import com.ll.blog.domain.global.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ApiEmailController {

    private final EmailService emailService;

    @PostMapping("/member/mailSend")
    public String mailSend(@RequestBody @Valid EmailRequest emailRequest) {
        log.info("이메일 인증 이메일: " + emailRequest.getEmail());
        return emailService.joinEmail(emailRequest.getEmail());
    }
}
