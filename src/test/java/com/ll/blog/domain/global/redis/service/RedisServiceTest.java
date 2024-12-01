package com.ll.blog.domain.global.redis.service;

import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisServiceTest {
    @Autowired
    private RedisService redisService;

    @Test
    @DisplayName("redis value값 테스트")
    void redisTest() {
        String key = "kyanghyang12@naver.com";
        String value = "123456";
        long duration = 60L;

        redisService.setDataExpire(key , value , duration);
        String redisValue = redisService.getData(key);
        assertThat(redisValue).isEqualTo(value);
        System.out.println("redis Value값 = " + redisValue);
    }
}