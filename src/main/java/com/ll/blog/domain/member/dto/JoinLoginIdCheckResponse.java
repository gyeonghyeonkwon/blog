package com.ll.blog.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JoinLoginIdCheckResponse {
    private String loginId; //사용하고 싶은 요청아이디
    private boolean availability; //사용 가능여부 false 면 사용가능
}
