package com.ll.blog.domain.member.controller;

import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.service.MemberService;
import groovy.util.logging.Slf4j;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signUp(MemberJoinRequest memberJoinRequest, Model model) {
        model.addAttribute("memberJoinRequest", memberJoinRequest);
        return "domain/member/signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid MemberJoinRequest memberJoinRequest) {
        memberService.signUp(memberJoinRequest);
        return "redirect:/member/login";
    }
    @GetMapping("/loginIdCheck")
    @ResponseBody
    public boolean loginIdCheck(@RequestParam("loginId") String loginId) {
        boolean loginIdCheck = memberService.isCheckLoginId(loginId);
        return loginIdCheck;
    }

    @GetMapping("/login")
    public String login() {
        return "domain/member/login";
    }
}
