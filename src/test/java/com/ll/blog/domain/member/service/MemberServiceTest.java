package com.ll.blog.domain.member.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ll.blog.domain.global.redis.config.RedisTestContainerConfig;
import com.ll.blog.domain.global.redis.service.RedisService;
import com.ll.blog.domain.member.dto.JoinLoginIdCheckRequest;
import com.ll.blog.domain.member.dto.MemberJoinCommand;
import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.dto.MemberJoinResponse;
import com.ll.blog.domain.member.dto.mapper.MemberMapper;
import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
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
@Transactional
class MemberServiceTest {

  @Autowired
  private MemberService memberService;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private RedisService redisService;

  @Autowired
  private MemberMapper memberMapper;

  @BeforeEach
  public void loginIdSave() {
    String loginId = "test88";
    Member member = Member.builder()
        .loginId(loginId)
        .build();
    memberRepository.save(member);
  }

  @BeforeEach
  public void codeSave() { //인증코드 발급
    String key = "test89@test.com"; //이메일
    String value = "123456"; //인증코드
    long duration = 60L; //만료시간

    redisService.setDataExpire(key, value, duration); // 레디스에 직접 데이터를 넣는다.
  }

  //테스트가 종료되면 redis 데이터를 삭제한다.
  @AfterEach
  public void codeDelete() {
    String key = "test89@test.com";
    redisService.deleteData(key);
  }

  @Test
  @DisplayName("아이디중복체크")
  void 로그인아이디중복테스트() {
    JoinLoginIdCheckRequest request = new JoinLoginIdCheckRequest("test88");
    boolean isExistsLoginId = memberService.isCheckLoginId(
        request.getLoginId()); //아이디가 중복이면 true가 반환되어야한다.
    System.out.println("로그인아이디중복여부 = " + isExistsLoginId);
    assertThat(isExistsLoginId).isTrue();
  }

  @Test
  @DisplayName("회원가입테스트")
  void signup() {
    MemberJoinRequest request = MemberJoinRequest.builder()
        .loginId("test89")
        .realName("테스터")
        .email("test89@test.com")
        .verificationCode("123456")
        .password("123")
        .passwordConfirm("123")
        .build();
    MemberJoinCommand joinCommand = memberMapper.toCommand(request);
    MemberJoinResponse memberJoinResponse = memberService.signUp(joinCommand);
    assertThat(memberJoinResponse.memberId()).isEqualTo(2L);
  }
}