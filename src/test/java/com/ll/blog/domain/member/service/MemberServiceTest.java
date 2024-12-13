package com.ll.blog.domain.member.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ll.blog.domain.global.redis.config.RedisTestContainerConfig;
import com.ll.blog.domain.global.redis.service.RedisService;
import com.ll.blog.domain.member.dto.JoinLoginIdCheckRequest;
import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
@ActiveProfiles("test")
@Import(RedisTestContainerConfig.class)
@SpringBootTest
class MemberServiceTest {

  @Autowired
  private MemberService memberService;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private RedisService redisService;

  @BeforeEach
  public void loginIdSave () {
    String loginId = "test88";
    Member member = Member.builder()
        .loginId(loginId)
        .build();
    memberRepository.save(member);
  }

  @Test
  @DisplayName("아이디중복체크")
  void 로그인아이디중복테스트() {
    JoinLoginIdCheckRequest request = new JoinLoginIdCheckRequest("test88");
    boolean isExistsLoginId = memberService.isCheckLoginId(request.getLoginId()); //아이디가 중복이면 true가 반환되어야한다.
    System.out.println("로그인아이디중복여부 = " + isExistsLoginId);
    assertThat(isExistsLoginId).isTrue();
  }
}