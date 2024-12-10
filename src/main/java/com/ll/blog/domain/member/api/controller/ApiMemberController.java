package com.ll.blog.domain.member.api.controller;

import com.ll.blog.domain.Email.dto.EmailRequest;
import com.ll.blog.domain.global.ResponseData;
import com.ll.blog.domain.member.dto.JoinLoginIdCheckRequest;
import com.ll.blog.domain.member.dto.JoinLoginIdCheckResponse;
import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.dto.MemberJoinResponse;
import com.ll.blog.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class ApiMemberController {

  private final MemberService memberService;

  @PostMapping("/check-login-id")
  public ResponseEntity loginIdCheck(@RequestBody @Valid JoinLoginIdCheckRequest joinLoginIdCheckRequest) {
    boolean isLoginIdCheck = memberService.isCheckLoginId(joinLoginIdCheckRequest.getLoginId());
    String idCheck = isLoginIdCheck ? "입력하신 아이디를 사용할 수 없습니다." : "아이디사용가능합니다.";
    return new ResponseEntity<>(ResponseData.res(200 , idCheck,
        new JoinLoginIdCheckResponse(joinLoginIdCheckRequest.getLoginId(), isLoginIdCheck)) , HttpStatus.OK);
  }

  @PostMapping("/check-email")
  public ResponseEntity checkEmail (@RequestBody @Valid EmailRequest emailRequest) {
    boolean isEmailCheck = memberService.isCheckEmail(emailRequest.getEmail());
    String emailCheck = isEmailCheck ? "입력하신 이메일을 사용할 수 없습니다," : "사용가능한이메일입니다.";
    return new ResponseEntity(ResponseData.res(200 , emailCheck , isEmailCheck) , HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity signUp(@RequestBody @Valid MemberJoinRequest memberJoinRequest) {
    Long memberJoinId =  memberService.signUp(memberJoinRequest);
    MemberJoinResponse memberJoinResponse = new MemberJoinResponse(memberJoinId,
        memberJoinRequest.getRealName(),
        memberJoinRequest.getLoginId(), memberJoinRequest.getEmail());
    return new ResponseEntity(ResponseData.res(200 , "회원가입성공" , memberJoinResponse) ,
        HttpStatus.OK);
  }
}
