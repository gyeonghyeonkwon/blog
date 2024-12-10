package com.ll.blog.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinResponse {
    private Long memberId;
    private String realName;
    private String loginId;
    private String email;
}
