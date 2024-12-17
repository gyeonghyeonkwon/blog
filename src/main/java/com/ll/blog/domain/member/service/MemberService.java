package com.ll.blog.domain.member.service;

import com.ll.blog.domain.Email.service.EmailService;
import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.dto.MemberJoinResponse;
import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberRepository memberRepository;
  private final EmailService emailService;

  @Transactional
  public MemberJoinResponse signUp(final MemberJoinRequest memberJoinRequest) {
    if (isCheckLoginId(memberJoinRequest.getLoginId())) {
      throw new IllegalArgumentException("아이디가 존재합니다.");
    }
    if (emailService.isCheckEmail(memberJoinRequest.getEmail())) {
      throw new IllegalArgumentException("이메일이 존재합니다.");
    }
    if (!memberJoinRequest.getPassword().equals(memberJoinRequest.getPasswordConfirm())) {
      throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
    }
    if (!emailService.verificationCodeCheck(memberJoinRequest.getEmail(),
        memberJoinRequest.getVerificationCode())) {
      throw new NullPointerException("인증코드가 일치하지않습니다.");
    }
    Member saveMember = memberRepository.save(memberJoinRequest.toEntity());
    return new MemberJoinResponse(saveMember);
  }
  //아이디 중복 여부
  public boolean isCheckLoginId(final String loginId) {
    return memberRepository.existsByLoginId(loginId);
  }
}
