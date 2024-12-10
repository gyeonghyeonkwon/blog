package com.ll.blog.domain.member.controller;

import com.ll.blog.domain.global.ResponseData;
import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.dto.MemberJoinResponse;
import com.ll.blog.domain.member.service.MemberService;
import groovy.util.logging.Slf4j;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

  @GetMapping("/signup")
  public String signUp(MemberJoinRequest memberJoinRequest, Model model) {
    model.addAttribute("memberJoinRequest", memberJoinRequest);
    return "domain/member/signup";
  }

  @GetMapping("/login")
  public String login() {
    return "domain/member/login";
  }
}
