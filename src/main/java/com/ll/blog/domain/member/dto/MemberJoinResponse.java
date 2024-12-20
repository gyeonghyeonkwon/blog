package com.ll.blog.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ll.blog.domain.member.entity.Member;
import java.time.LocalDateTime;

public record MemberJoinResponse(Long memberId, String loginId, String realName, String email,
                                 String role,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
                                 LocalDateTime createdAt) {

  public MemberJoinResponse(Member member) {
    this(
        member.getMemberId(), member.getLoginId(), member.getRealName(), member.getEmail(),
        member.getRole().getValue(),
        member.getCreateDate()
    );
  }
}