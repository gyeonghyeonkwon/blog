package com.ll.blog.domain.Email.api.controller;

import com.ll.blog.domain.Email.dto.EmailCodeCheckRequest;
import com.ll.blog.domain.Email.dto.EmailCommand;
import com.ll.blog.domain.Email.dto.EmailRequest;
import com.ll.blog.domain.Email.dto.EmailResponse;
import com.ll.blog.domain.Email.service.EmailService;
import com.ll.blog.domain.global.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class ApiEmailController {

  private final EmailService emailService;

  @PostMapping("/check-email")
  public ResponseEntity checkEmail(@RequestBody @Valid EmailRequest emailRequest) {
    EmailCommand emailCommand = emailRequest.toCommand();
    boolean isEmailCheck = emailService.isCheckEmail(emailCommand.getEmail());
    String emailCheck = isEmailCheck ? "사용가능한이메일입니다." : "입력하신 이메일을 사용할 수 없습니다";
    return new ResponseEntity<>(
        ResponseData.res(200, emailCheck, new EmailResponse(isEmailCheck)),
        HttpStatus.OK);
  }

  @PostMapping("/mail-send")
  public ResponseEntity mailSend(@RequestBody @Valid EmailRequest emailRequest) {
    String verificationCode = emailService.joinEmail(emailRequest.getEmail());
    return new ResponseEntity<>(
        ResponseData.res(200, "인증번호가 전송되었습니다. 이메일을 확인해주세요.", verificationCode),
        HttpStatus.OK); //인증번호 6자리
  }

  @PostMapping("/verification-code")
  public ResponseEntity mailCode(@RequestBody @Valid EmailCodeCheckRequest emailCodeCheckRequest) {
    boolean isCodeCheck = emailService.verificationCodeCheck(emailCodeCheckRequest.getEmail(),
        emailCodeCheckRequest.getVerificationCode());
    String isSuccess = isCodeCheck ? "인증에성공하였습니다." : "인증에실패하였습니다.";
    return new ResponseEntity<>(ResponseData.res(200, isSuccess, isCodeCheck), HttpStatus.OK);
  }
}
