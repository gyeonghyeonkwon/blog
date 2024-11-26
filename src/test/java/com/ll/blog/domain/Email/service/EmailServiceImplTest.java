package com.ll.blog.domain.Email.service;

import com.ll.blog.domain.Email.util.RandomValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        String test = emailService.joinEmail("kyanghyang12@naver.com");
        System.out.println(test);
    }
}