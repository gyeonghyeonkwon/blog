package com.ll.blog.domain.member.api.controller;

import com.ll.blog.domain.member.dto.JoinLoginIdCheckRequest;
import com.ll.blog.domain.member.dto.JoinLoginIdCheckResponse;
import com.ll.blog.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiMemberController {

  private final MemberService memberService;

  @PostMapping("/member/loginIdCheck")
  public ResponseEntity loginIdCheck(@RequestBody @Valid JoinLoginIdCheckRequest joinLoginIdCheckRequest) {
    boolean isLoginIdCheck = memberService.isCheckLoginId(joinLoginIdCheckRequest.getLoginId());
    JoinLoginIdCheckResponse joinLoginIdCheckResponse = new JoinLoginIdCheckResponse(joinLoginIdCheckRequest.getLoginId(), isLoginIdCheck);
    return new ResponseEntity<>(joinLoginIdCheckResponse, HttpStatus.OK);
  }
}
