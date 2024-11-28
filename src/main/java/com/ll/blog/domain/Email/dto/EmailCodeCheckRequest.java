package com.ll.blog.domain.Email.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCodeCheckRequest {

    @NotBlank
    private int  verificationCode; //인증번호
}
