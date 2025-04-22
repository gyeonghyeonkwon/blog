package com.ll.blog.domain.member.service;

import com.ll.blog.domain.Email.service.EmailService;
import com.ll.blog.domain.global.redis.service.RedisService;
import com.ll.blog.domain.jwt.Jwt;
import com.ll.blog.domain.jwt.JwtProvider;
import com.ll.blog.domain.member.dto.LoginRequest;
import com.ll.blog.domain.member.dto.MemberJoinCommand;
import com.ll.blog.domain.member.dto.MemberJoinResponse;
import com.ll.blog.domain.member.dto.TokenRequest;
import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
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
  private final RedisService redisService;
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
    //redis에 refreshToken저장
    redisService.setData(authentication.getName(), jwt.getRefreshToken());
    return jwt;
  }

  public void logout(HttpServletRequest request) {
    String accessToken = getAccessToken(request);
    if (!jwtProvider.isValidateToken(accessToken)) {
      throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
    }
    Authentication authentication = jwtProvider.getAuthentication(accessToken);
    String username = authentication.getName();
    long expireTime = jwtProvider.getRemainingExpiration(accessToken);
    redisService.setDataExpire(accessToken, accessToken, expireTime); //black list
    redisService.deleteData(username); //refreshToken 삭제
  }

  @Transactional
  public Jwt reissue(TokenRequest tokenRequest) {
    // 1. Refresh Token 검증
    if (!jwtProvider.isValidateToken(tokenRequest.getRefreshToken())) {
      throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
    }
    // 2. Access Token 에서 Member ID 가져오기
    Authentication authentication = jwtProvider.getAuthentication(tokenRequest.getAccessToken());

    // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
    String refreshToken = redisService.getData(authentication.getName()); //value값
    if (refreshToken == null) {
      throw new RuntimeException("로그아웃 된 사용자입니다.");
    }
    // 4. Refresh Token 일치하는지 검사
    if (!refreshToken.equals(tokenRequest.getRefreshToken())) {
      throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
    }
    // 5. 새로운 토큰 생성
    Jwt jwt = jwtProvider.generateToken(authentication);

    // 6. 저장소 정보 업데이트
    redisService.setData(authentication.getName(), jwt.getRefreshToken());
    return jwt;
  }

  private String getAccessToken(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    if (header == null || !header.startsWith("Bearer ")) {
      throw new IllegalArgumentException("AccessToken이 없습니다.");
    }
    return header.substring(7);
  }
}
