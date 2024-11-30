package com.ll.blog.domain.Email.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

public interface EmailService {
    String joinEmail(String email);

    void mailSend(String setFrom, String toMail, String title, String content);

    Boolean verificationCodeCheck(String email , String verificationCode);

    int randomValueGeneration();

    String htmlContent(int randomValue);
}
