package com.ll.blog.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinLoginIdCheckRequest {
    @NotBlank(message = "로그인 아이디를 입력해주세요.")
    private String loginId;
}
