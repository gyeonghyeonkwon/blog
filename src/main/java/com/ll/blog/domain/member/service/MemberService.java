package com.ll.blog.domain.member.service;

import com.ll.blog.domain.Email.service.EmailService;
import com.ll.blog.domain.jwt.Jwt;
import com.ll.blog.domain.jwt.JwtProvider;
import com.ll.blog.domain.member.dto.LoginRequest;
import com.ll.blog.domain.member.dto.MemberJoinCommand;
import com.ll.blog.domain.member.dto.MemberJoinResponse;
import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberRepository memberRepository;
  private final EmailService emailService;
  private final JwtProvider jwtProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @Transactional
  public MemberJoinResponse signUp(final MemberJoinCommand memberJoinCommand) {
    if (isCheckLoginId(memberJoinCommand.getLoginId())) {
      throw new IllegalArgumentException("아이디가 존재합니다.");
    }
    if (!emailService.isCheckEmail(memberJoinCommand.getEmail())) {
      throw new IllegalArgumentException("이메일이 존재합니다.");
    }
    if (!memberJoinCommand.getPassword().equals(memberJoinCommand.getPasswordConfirm())) {
      throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
    }
    if (!emailService.verificationCodeCheck(memberJoinCommand.getEmail(),
        memberJoinCommand.getVerificationCode())) {
      throw new NullPointerException("인증코드가 일치하지않습니다.");
    }
    Member saveMember = memberRepository.save(memberJoinCommand.toEntity());
    return new MemberJoinResponse(saveMember);
  }

  //아이디 중복 여부
  public boolean isCheckLoginId(final String loginId) {
    return memberRepository.existsByLoginId(loginId);
  }

  @Transactional
  public Jwt login(LoginRequest loginRequest) {
    // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        loginRequest.getLoginId()
        , loginRequest.getPassword());
    // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
    //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(authenticationToken);
    // 3. 인증 정보를 기반으로 JWT 토큰 생성 (access + refresh token)
    Jwt jwt = jwtProvider.generateToken(authentication);

    /**
     * refreshToken 구현 예정
     */

    return jwt;
  }
}
