package com.ll.blog.domain.Email.util;

import java.util.Random;

public class RandomValue {

    Random random = new Random();

    public int getRandomValue() {
        StringBuilder randomValue = new StringBuilder();
        for (int i = 1; i <= 6; i++) {
            randomValue.append(random.nextInt(9) + 1) ; // 1 ~ 9 까지의 임의의 숫자를 한개씩 6번 누적
        }
        return Integer.parseInt(randomValue.toString());
    }
}
