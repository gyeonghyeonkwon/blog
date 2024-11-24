package com.ll.blog.domain.member.controller;

import com.ll.blog.domain.global.ResponseData;
import com.ll.blog.domain.member.dto.JoinLoginIdCheckRequest;
import com.ll.blog.domain.member.dto.JoinLoginIdCheckResponse;
import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.service.MemberService;
import groovy.util.logging.Slf4j;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    @PostMapping("/loginIdCheck")
    @ResponseBody
    public ResponseEntity loginIdCheck(@RequestBody JoinLoginIdCheckRequest joinLoginIdCheckRequest) {
        boolean isLoginIdCheck =  memberService.isCheckLoginId(joinLoginIdCheckRequest.getLoginId());
        JoinLoginIdCheckResponse joinLoginIdCheckResponse = new JoinLoginIdCheckResponse(joinLoginIdCheckRequest.getLoginId(), isLoginIdCheck);
        return new ResponseEntity<>(joinLoginIdCheckResponse, HttpStatus.OK);
    }

    @GetMapping("/login")
    public String login() {
        return "domain/member/login";
    }
}
