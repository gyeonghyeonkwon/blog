package com.ll.blog.domain.Email.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmailServiceImplTest {
    @Autowired
    private EmailService emailService;

    @Test
    void joinEmail() {
        String authCode = emailService.joinEmail("kyanghyang12@naver.com");
        System.out.println(authCode);
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
    void sendMainContent() {
        int randomValue = 123456;
        String test = emailService.htmlContent(randomValue);
        System.out.println(test);
    }
}