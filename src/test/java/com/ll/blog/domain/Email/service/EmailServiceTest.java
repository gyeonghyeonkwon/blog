package com.ll.blog.domain.Email.service;

import com.ll.blog.domain.global.redis.service.RedisService;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static jakarta.validation.constraints.Pattern.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class EmailServiceTest {

  @Autowired
  private EmailService emailService;
  @Autowired
  private RedisService redisService;

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
  void sendMainContent() {
    int randomValue = 123456;
    String test = emailService.htmlContent(randomValue);
    System.out.println(test);
  }

  @Test
  @DisplayName("메일로 전송한 인증번호 와 redis 에 저장된 인증번호 일치 테스트")
  void joinEmail() {
    String key = "kyanghyang12@naver.com"; //key
    String authCode = emailService.joinEmail(key); // 인증번호를 메일로 전송
    String value = redisService.getData(key); //redis value 와 메일로전송한 인증번호와 일치하는지 확인
    assertThat(authCode).isEqualTo(value);
    System.out.println(authCode);
  }

  @Test
  @DisplayName("이메일테스트")
  void emailTest() {
    String emailCheck = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    String email1 = "test1@test.com";
    String email2 = "test2@test.co.kr";
    String email3 = "test3@test.ac.kr";
    String email4 = "test4@test.seoul.kr";
    String email5 = "test5@test.net";
    String email6 = "test6@testnet"; //실패 테스트


    boolean result1 = java.util.regex.Pattern.matches(emailCheck , email1);
    boolean result2 = java.util.regex.Pattern.matches(emailCheck , email2);
    boolean result3 = java.util.regex.Pattern.matches(emailCheck , email3);
    boolean result4 = java.util.regex.Pattern.matches(emailCheck , email4);
    boolean result5 = java.util.regex.Pattern.matches(emailCheck , email5);
    boolean result6 = java.util.regex.Pattern.matches(emailCheck , email6);

    System.out.println(result1);
    System.out.println(result2);
    System.out.println(result3);
    System.out.println(result4);
    System.out.println(result5);
    System.out.println(result6);
  }
}