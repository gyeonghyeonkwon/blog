package com.ll.blog.domain.member.api.controller;

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

  @PostMapping("/loginIdCheck")
  public ResponseEntity loginIdCheck(@RequestBody @Valid JoinLoginIdCheckRequest joinLoginIdCheckRequest) {
    boolean isLoginIdCheck = memberService.isCheckLoginId(joinLoginIdCheckRequest.getLoginId());
    String idCheck = isLoginIdCheck ? "아이디가 중복입니다." : "아이디사용가능합니다.";
    return new ResponseEntity<>(ResponseData.res(200 , idCheck,
        new JoinLoginIdCheckResponse(joinLoginIdCheckRequest.getLoginId(), isLoginIdCheck)) , HttpStatus.OK);
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
