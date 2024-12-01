package com.ll.blog.domain.Email.api.controller;

import com.ll.blog.domain.Email.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ApiEmailControllerTest.class})
@ImportAutoConfiguration(exclude = SecurityAutoConfiguration.class) // Security 비활성화
class ApiEmailControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmailService emailService;

    @Test
    @DisplayName("메일전송 테스트")
//    @WithMockUser(username = "testUser") // 인증된 사용자로 테스트
    void mailSend() throws Exception {
        String api = "/api/member/mailSend";
        String authCode = "123456";
        when(emailService.joinEmail("kyanghyang12@naver.com")).thenReturn(authCode);

        ResultActions actions = mockMvc.perform(post(api));

        actions.andExpect(status().isOk())
                .andExpect(content().string(authCode))
                .andDo(print());
    }

    @Test
    @DisplayName("인증번호체크 테스트")
    void mailCode() {
    }
}