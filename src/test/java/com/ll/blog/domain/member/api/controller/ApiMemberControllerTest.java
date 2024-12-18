package com.ll.blog.domain.member.api.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ll.blog.domain.member.dto.JoinLoginIdCheckRequest;
import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.restdocs.RestDocsTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class ApiMemberControllerTest extends RestDocsTestSupport {

  @Test
  @DisplayName("로그인중복체크테스트")
  void loginIdCheck() throws Exception {
    String api = "/api/member/check-login-id";
    String loginId = "example1";
    String requestBody = objectMapper.writeValueAsString(
        new JoinLoginIdCheckRequest(loginId)); //로그인아이디객체 -> json 직렬화

    //request
    ResultActions actions = mockMvc.perform(post(api)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .characterEncoding("utf-8")
        .content(requestBody)); //loginId 를 json으로 변환하여 요청

    //response
    actions.andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value("200"))
        .andExpect((jsonPath("$.responseMessage").value("아이디사용가능합니다.")))
        .andExpect(jsonPath("$.data.loginId").value("example1"))
        .andExpect(jsonPath("$.data.availability").value(false))
        .andDo(
            restDocs.document(
                requestFields(
                    fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디")
//                          .attributes(key("constraints").value("2자 이상 4자 이하 형식"))
                        .attributes(constraints("5자 이상 12자 미만 , 영 소.대문자 + 숫자조합"))
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("responseMessage").type(JsonFieldType.STRING)
                        .description("응답메세지"),
                    fieldWithPath("data.loginId").type(JsonFieldType.STRING).description("로그인아이디"),
                    fieldWithPath("data.availability").type(JsonFieldType.BOOLEAN)
                        .description("중복여부")
                )));
  }

  @Test
  @DisplayName("회원가입테스트")
  void signUp() throws Exception {
    String api = "/api/member/signup";
    String loginId = "example1";
    String realName = "홍길동";
    String email = "example@example.com";
    String verificationCode = "123456";
    String password = "123";
    String passwordConfirm = "123";
    String requestBody = objectMapper.writeValueAsString(
        new MemberJoinRequest(loginId, realName, email, verificationCode, password,
            passwordConfirm));

    //request
    ResultActions actions = mockMvc.perform(post(api)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .characterEncoding("utf-8")
        .content(requestBody));

    //response
    actions.andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value("201"))
        .andExpect((jsonPath("$.responseMessage").value("회원가입성공")))
        .andExpect((jsonPath("$.data.memberId").value(1)))
        .andExpect((jsonPath("$.data.realName").value("홍길동")))
        .andExpect((jsonPath("$.data.loginId").value("example1")))
        .andExpect((jsonPath("$.data.email").value("example@example.com")))
        .andExpect((jsonPath("$.data.role").value("MEMBER")))
        .andDo(
            restDocs.document(
                requestFields(
                    fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디")
                        .attributes(constraints("5자 이상 12자 미만 , 영 소.대문자 + 숫자조합")),
                    fieldWithPath("realName").type(JsonFieldType.STRING).description("본명")
                        .attributes(constraints("한글만 입력가능 , 3 ~ 4 자 작성가능")),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                        .attributes(constraints("이메일형식")),
                    fieldWithPath("verificationCode").type(JsonFieldType.STRING).description("인증번호")
                        .attributes(constraints("숫자 6자리")),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        .attributes(constraints("유효성적용 x")),
                    fieldWithPath("passwordConfirm").type(JsonFieldType.STRING)
                        .description("비밀번호 확인")
                        .attributes(constraints("유효성적용 x"))
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("responseMessage").type(JsonFieldType.STRING)
                        .description("응답메세지"),
                    fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원아이디"),
                    fieldWithPath("data.loginId").type(JsonFieldType.STRING).description("로그인아이디"),
                    fieldWithPath("data.realName").type(JsonFieldType.STRING).description("본명"),
                    fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("data.role").type(JsonFieldType.STRING).description("권한"),
                    fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성시간")
                )
            )
        );
  }
}