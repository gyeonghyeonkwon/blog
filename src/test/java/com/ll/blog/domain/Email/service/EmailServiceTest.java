package com.ll.blog.domain.Email.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ll.blog.domain.Email.dto.EmailCodeCheckRequest;
import com.ll.blog.domain.Email.dto.EmailRequest;
import com.ll.blog.domain.global.redis.config.RedisTestContainerConfig;
import com.ll.blog.domain.global.redis.service.RedisService;
import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.repository.MemberRepository;
import jakarta.mail.internet.MimeMessage;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(RedisTestContainerConfig.class)
class EmailServiceTest {

  @Autowired
  private EmailService emailService;
  @Autowired
  private RedisService redisService;
  @Autowired
  private MemberRepository memberRepository;
  @MockBean
  private JavaMailSender mailSender;

  @BeforeEach
  public void emailSave() {
    String email = "test88@test.com";
    Member member = Member.builder()
        .email(email)
        .build();
    memberRepository.save(member);
  }

  @BeforeEach
  public void codeSave() { //인증코드 발급
    String key = "test88@test.com"; //이메일
    String value = "123456"; //인증코드
    long duration = 60L; //만료시간

    redisService.setDataExpire(key, value, duration); // 레디스에 직접 데이터를 넣는다.
  }
  //테스트가 종료되면 redis 데이터를 삭제한다.
  @AfterEach
  public void codeDelete() {
    String key = "test88@test.com";
    redisService.deleteData(key);
  }

  @Test
  @DisplayName("이메일 중복테스트")
  void 이메일중복테스트() {
    EmailRequest request = new EmailRequest("test88@test.com");
    boolean isExistsEmail = emailService.isCheckEmail(request.getEmail());
    System.out.println("이메일중복여부 = " + isExistsEmail);
    assertThat(isExistsEmail).isTrue();
  }

  @Test
  @DisplayName("6자리의 난수테스트")
  void randomValue() {
    String value = String.valueOf(emailService.randomValueGeneration());
    String[] arrayValue = value.split("");
    System.out.println("랜덤숫자 값: " + Arrays.toString(arrayValue));
    assertThat(arrayValue.length).isEqualTo(6);
  }

  @Test
  @DisplayName("메일내용테스트")
  void sendMailContent() {
    int randomValue = 123456;
    String test = emailService.htmlContent(randomValue);
    System.out.println(test);
  }

  @Test
  @DisplayName("이메일로 발급받은 인증코드 일치테스트")
  void emailCodeCheck() {
    String email = "test88@test.com";
    String code = "123456";
    EmailCodeCheckRequest request = new EmailCodeCheckRequest(email , code); //인증코드 확인 dto
    boolean isCodeCheck = emailService.verificationCodeCheck(request.getEmail() , request.getVerificationCode());

    assertThat(isCodeCheck).isTrue();
  }

  @Test
  @DisplayName("이메일전송테스트")
  void sendEmail() throws Exception{
    MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    String code = emailService.joinEmail("test89@test.com");
    verify(mailSender, times(1)).send(mimeMessage);

    assertThat(6).isEqualTo(code.length()); //인증번호 6자리맞는지 확인
  }

  @Test
  @DisplayName("이메일 정규표현식테스트")
  void emailTest() {
    String emailCheck = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    String[] emails = {"test1@test.com" , "test2@test.co.kr" , "test3@test.ac.kr" , "test4@test.seoul.kr" ,
        "test5@test.net" , "test6@testnet"};

    for (int i = 0; i < emails.length; i++) {
      boolean isResult = java.util.regex.Pattern.matches(emailCheck , emails[i]);
      System.out.println(isResult);
    }
  }
}