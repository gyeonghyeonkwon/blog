package com.ll.blog.domain.Email.service;

import org.springframework.stereotype.Service;

public interface EmailService {
    String joinEmail(String email);
    void mailSend(String setFrom, String toMail, String title, String content);
}
