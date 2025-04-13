package com.ll.blog.domain.member.controller;

import com.ll.blog.domain.member.dto.MemberJoinRequest;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
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
