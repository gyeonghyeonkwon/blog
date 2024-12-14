package com.ll.blog.domain.member.api.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ll.blog.domain.member.dto.JoinLoginIdCheckRequest;
import com.ll.blog.domain.restdocs.RestDocsTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

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
        .content(requestBody)); //loginId 를 json으로 변환하여 요청

    //response
    actions.andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value("200"))
        .andExpect((jsonPath("$.responseMessage").value("아이디사용가능합니다.")))
        .andExpect(jsonPath("$.data.loginId").value("example1"))
        .andExpect(jsonPath("$.data.availability").value(false))
        .andDo(document("{class-name}/{method-name}", //테스트클래스이름 , 테스트메소드이름의 패키지가 만들어진다. 패키지안에 스니펫들이만들어진다.
            preprocessRequest(removeHeaders("Host" , "Content-Length") , prettyPrint()),
            preprocessResponse(prettyPrint()),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("responseMessage").type(JsonFieldType.STRING).description("응답메세지"),
                fieldWithPath("data.loginId").type(JsonFieldType.STRING).description("로그인아이디"),
                fieldWithPath("data.availability").type(JsonFieldType.BOOLEAN).description("중복여부")
            )));
  }

  @Test
  @DisplayName("회원가입테스트")
  void signUp() {

  }
}