package com.ll.blog.domain.member.api.controller;

import com.ll.blog.domain.global.ResponseData;
import com.ll.blog.domain.member.dto.JoinLoginIdCheckRequest;
import com.ll.blog.domain.member.dto.JoinLoginIdCheckResponse;
import com.ll.blog.domain.member.dto.LoginRequest;
import com.ll.blog.domain.member.dto.MemberJoinCommand;
import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.dto.MemberJoinResponse;
import com.ll.blog.domain.member.dto.TokenRequest;
import com.ll.blog.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class ApiMemberController {

  private final MemberService memberService;

  @PostMapping("/check-login-id")
  public ResponseEntity loginIdCheck(
      @RequestBody @Valid JoinLoginIdCheckRequest joinLoginIdCheckRequest) {
    boolean isLoginIdCheck = memberService.isCheckLoginId(
        joinLoginIdCheckRequest.getLoginId());
    String idCheck = isLoginIdCheck ? "입력하신 아이디를 사용할 수 없습니다." : "아이디사용가능합니다.";
    return new ResponseEntity<>(ResponseData.res(200, idCheck,
        new JoinLoginIdCheckResponse(joinLoginIdCheckRequest.getLoginId(), isLoginIdCheck)),
        HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity signUp(@RequestBody @Valid MemberJoinRequest memberJoinRequest) {
    MemberJoinCommand joinCommand = memberJoinRequest.toCommand();
    MemberJoinResponse memberJoinResponse = memberService.signUp(joinCommand);
    return new ResponseEntity(ResponseData.res(201, "회원가입성공", memberJoinResponse),
        HttpStatus.OK);
  }

  @PostMapping("/signIn")
  public ResponseEntity login(@RequestBody @Valid LoginRequest loginRequest) {
    return new ResponseEntity(ResponseData.res(200, "로그인성공", memberService.login(loginRequest)),
        HttpStatus.OK);
  }

  @PostMapping("/logout")
  public ResponseEntity logout(HttpServletRequest request) {
    memberService.logout(request);
    return new ResponseEntity(ResponseData.res(200, "로그아웃완료", ""), HttpStatus.OK);
  }

  @PostMapping("/reissue")
  public ResponseEntity reissue(@RequestBody TokenRequest tokenRequest) {
    return new ResponseEntity(ResponseData.res(200,
        "토큰재발급성공", memberService.reissue(tokenRequest)), HttpStatus.OK);
  }

  @GetMapping("/me")
  public ResponseEntity getUserInfo(Authentication authentication) {
    if (authentication == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않음");
    }
    String username = authentication.getName();
    return new ResponseEntity(ResponseData.res(200, "조회성공", username), HttpStatus.OK);
  }
}
