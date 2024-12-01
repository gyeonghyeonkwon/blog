package com.ll.blog.domain.Email.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.blog.domain.Email.dto.EmailCodeCheckRequest;
import com.ll.blog.domain.Email.dto.EmailRequest;
import com.ll.blog.domain.Email.service.EmailService;
import com.ll.blog.domain.global.redis.service.RedisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApiEmailControllerTest {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  private RedisService redisService;

  @Test
  @DisplayName("메일전송 테스트")
  void mailSend() throws Exception {
    String api = "/api/member/mailSend";
    String email = "kyanghyang12@naver.com";
    String requestBody = objectMapper.writeValueAsString(
        new EmailRequest(email)); //이메일객체 -> json 직렬화

    //request
    ResultActions actions = mockMvc.perform(post(api)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestBody));

    //response
    actions.andExpect(status().isOk())
        .andExpect(jsonPath("$.data").exists()) //반환값에 데이터가존재하는지
        .andExpect(jsonPath("$.data").value(matchesPattern("\\d{6}"))) // 데이터 6자리테스트
        .andDo(print());
  }

  @Test
  @DisplayName("인증번호체크 테스트")
  void mailCode() throws Exception {
    String api = "/api/member/verificationCode";
    String email = "kyanghyang12@naver.com";
    String value = redisService.getData(email);

    String requestBody = objectMapper.writeValueAsString(new EmailCodeCheckRequest(
        email, value)); //이메일객체 -> json 직렬화

    //request
    ResultActions actions = mockMvc.perform(post(api)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestBody));
    //response
    actions.andExpect(status().isOk())
        .andDo(print());
  }
}