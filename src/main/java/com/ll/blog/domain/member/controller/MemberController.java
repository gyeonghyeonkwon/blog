package com.ll.blog.domain.member.controller;

import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signUp(MemberJoinRequest memberJoinRequest) {
        return "domain/member/signup.html";
    }

    @PostMapping("/signup")
    public String signUp(@Valid MemberJoinRequest memberJoinRequest , BindingResult bindingResult) {
        memberService.signUp(memberJoinRequest);
        return "domain/member/signup.html";
    }
}
