package com.ll.blog.domain.member.dto;

import com.ll.blog.domain.member.entity.Member;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MemberJoinResponse {

  private final Long memberId;
  private final String realName;
  private final String loginId;
  private final String email;
  private final String role;
  private final LocalDateTime createdAt;

  public MemberJoinResponse(Member member) {
    this.memberId = member.getMemberId();
    this.realName = member.getRealName();
    this.loginId = member.getLoginId();
    this.email = member.getEmail();
    this.role = member.getRole().getValue();
    this.createdAt = member.getCreateDate();
  }
}
