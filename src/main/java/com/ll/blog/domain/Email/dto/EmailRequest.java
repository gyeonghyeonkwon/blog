package com.ll.blog.domain.Email.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class EmailRequest {
    @Email
    private String email;
}
