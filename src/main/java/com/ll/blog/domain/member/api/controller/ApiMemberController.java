package com.ll.blog.domain.member.api.controller;

import com.ll.blog.domain.global.ResponseData;
import com.ll.blog.domain.jwt.Jwt;
import com.ll.blog.domain.member.dto.JoinLoginIdCheckRequest;
import com.ll.blog.domain.member.dto.JoinLoginIdCheckResponse;
import com.ll.blog.domain.member.dto.LoginRequest;
import com.ll.blog.domain.member.dto.MemberJoinCommand;
import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.dto.MemberJoinResponse;
import com.ll.blog.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
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

  @PostMapping("/login")
  public ResponseEntity<Jwt> login(@RequestBody @Valid LoginRequest loginRequest) {
    return ResponseEntity.ok(memberService.login(loginRequest));
  }
}
