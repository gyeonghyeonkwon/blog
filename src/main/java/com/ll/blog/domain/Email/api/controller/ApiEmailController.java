package com.ll.blog.domain.Email.api.controller;

import com.ll.blog.domain.Email.dto.EmailCodeCheckRequest;
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
@RequestMapping("/api/member")
@Slf4j
public class ApiEmailController {

  private final EmailService emailService;

  @PostMapping("/mail-send")
  public ResponseEntity mailSend(@RequestBody @Valid EmailRequest emailRequest) {
    String verificationCode = emailService.joinEmail(emailRequest.getEmail());
    return new ResponseEntity<>(ResponseData.res(200, "인증번호를확인해주세요.", verificationCode),
        HttpStatus.OK); //인증번호 6자리
  }

  @PostMapping("/verification-code")
  public ResponseEntity mailCode(@RequestBody @Valid EmailCodeCheckRequest emailCodeCheckRequest) {
    Boolean codeCheck = emailService.verificationCodeCheck(emailCodeCheckRequest.getEmail(),
        emailCodeCheckRequest.getVerificationCode());
    String isSuccess = codeCheck ? "인증에성공하였습니다." : "인증에실패하였습니다.";
      return new ResponseEntity<>(ResponseData.res(200, isSuccess , codeCheck), HttpStatus.OK);
  }
}
