package com.ll.blog.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ll.blog.domain.member.entity.Member;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MemberJoinResponse {

  private final Long memberId;
  private final String loginId;
  private final String realName;
  private final String email;
  private final String role;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",
      timezone = "Asia/Seoul")
  private final LocalDateTime createdAt;

  public MemberJoinResponse(Member member) {
    this.memberId = member.getMemberId();
    this.loginId = member.getLoginId();
    this.realName = member.getRealName();
    this.email = member.getEmail();
    this.role = member.getRole().getValue();
    this.createdAt = member.getCreateDate();
  }
}
