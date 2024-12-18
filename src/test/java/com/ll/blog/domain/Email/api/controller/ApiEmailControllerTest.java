package com.ll.blog.domain.Email.api.controller;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ll.blog.domain.Email.dto.EmailCodeCheckRequest;
import com.ll.blog.domain.Email.dto.EmailRequest;
import com.ll.blog.domain.global.redis.service.RedisService;
import com.ll.blog.domain.restdocs.RestDocsTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class ApiEmailControllerTest extends RestDocsTestSupport {

  @Autowired
  private RedisService redisService;

  @Test
  @DisplayName("메일전송 테스트")
  void mailSend() throws Exception {
    String api = "/api/member/mail-send";
    String email = "example@example.com";
    String requestBody = objectMapper.writeValueAsString(
        new EmailRequest(email)); //이메일객체 -> json 직렬화

    //request
    ResultActions actions = mockMvc.perform(post(api)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestBody));

    //response
    actions.andExpect(status().isOk())
        .andExpect(jsonPath("$.data").exists()) //반환값에 데이터가존재하는지
        .andExpect(jsonPath("$.statusCode").value("200"))
        .andExpect((jsonPath("$.responseMessage").value("인증번호가 전송되었습니다. 이메일을 확인해주세요.")))
        .andExpect(jsonPath("$.data").value(matchesPattern("\\d{6}"))) // 인증번호 6자리테스트
        .andDo(
            restDocs.document(
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                        .attributes(constraints("이메일 형식"))
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("responseMessage").type(JsonFieldType.STRING)
                        .description("응답메세지"),
                    fieldWithPath("data").type(JsonFieldType.STRING).description("인증번호")
                )
            )
        );
  }

  @Test
  @DisplayName("인증번호체크 테스트")
  void mailCode() throws Exception {
    String api = "/api/member/verification-code";
    String email = "example@example.com";
    String value = redisService.getData(email);

    String requestBody = objectMapper.writeValueAsString(new EmailCodeCheckRequest(
        email, value)); //이메일객체 -> json 직렬화

    //request
    ResultActions actions = mockMvc.perform(post(api)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestBody));
    //response
    actions.andExpect(status().isOk())
        .andExpect(jsonPath(("$.statusCode")).value("200"))
        .andExpect(jsonPath(("$.responseMessage")).value("인증에성공하였습니다."))
        .andExpect(jsonPath(("$.data")).value(true))
        .andDo(
            restDocs.document(
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                        .attributes(constraints("이메일 형식")),
                    fieldWithPath("verificationCode").type(JsonFieldType.STRING).description("인증번호")
                        .attributes(constraints("숫자 6자리"))
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("responseMessage").type(JsonFieldType.STRING)
                        .description("응답메세지"),
                    fieldWithPath("data").type(JsonFieldType.BOOLEAN).description(true)
                )
            )
        );
  }
}