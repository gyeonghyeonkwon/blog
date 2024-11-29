package com.ll.blog.domain.Email.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomValue {

    SecureRandom secureRandom = new SecureRandom();

    public int getRandomValue() {
        StringBuilder randomValue = new StringBuilder();
        for (int i = 1; i <= 6; i++) {
            randomValue.append(secureRandom.nextInt(10)) ; // 0 ~ 9 까지의 임의의 숫자를 한개씩 6번 누적
        }
        return Integer.parseInt(randomValue.toString());
    }
}
