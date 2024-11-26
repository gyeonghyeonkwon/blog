package com.ll.blog.domain.Email.util;

import com.ll.blog.domain.Email.service.EmailServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RandomValueTest {
    @Autowired
    private EmailServiceImpl emailService;

    @Test
    @DisplayName("6자리의 난수테스트")
    void randomValue() {
        RandomValue randomValue = new RandomValue();
        String value = String.valueOf(randomValue.getRandomValue());
        String[] arrayValue = value.split("");
        System.out.println("랜덤숫자 값: " + Arrays.toString(arrayValue));
        assertThat(arrayValue.length).isEqualTo(6);
    }
}