package com.ll.blog.domain.member.service;

import com.ll.blog.domain.Email.service.EmailService;
import com.ll.blog.domain.global.redis.RedisTestContainerConfig;
import com.ll.blog.domain.global.redis.service.RedisService;
import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
@ActiveProfiles("test")
//@ExtendWith(MockitoExtension.class)
@Import(RedisTestContainerConfig.class)
@SpringBootTest
class MemberServiceTest {
  @InjectMocks
  private MemberService memberService;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private EmailService emailService;

  @Mock
  private RedisService redisService;

  @Test
  @DisplayName("회원가입 시 아이디중복체크")
  void 회원가입테스트() {
    String email = "hongildong@test.com";
    String code = "123456";
    long duration = 60 * 5L;
    redisService.setDataExpire(email, code, duration);

    String value = redisService.getData(email);
    System.out.println("value = " + value);
//    MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
//        .loginId("hongildong")
//        .realName("홍길동")
//        .email("kyanghyang12@naver.com")
//        .verificationCode("123456")
//        .password("1234")
//        .passwordConfirm("1234")
//        .build();
//    when(memberRepository.existsByLoginId(memberJoinRequest.getLoginId())).thenReturn(
//        false); //로그인아이디 중복확인
//    when(memberRepository.existsByEmail(memberJoinRequest.getEmail())).thenReturn(
//        false);
//    memberService.signUp(memberJoinRequest);
//    verify(memberRepository, times(1)).save(any());
  }
}