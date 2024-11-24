package com.ll.blog.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JoinLoginIdCheckResponse {
    private String loginId;
    private boolean availability;
}
