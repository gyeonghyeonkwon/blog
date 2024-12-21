package com.ll.blog.domain.member.dto;

import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberJoinCommand {

  private String loginId;
  private String realName;
  private String email;
  private String verificationCode;
  private String password;
  private String passwordConfirm;
  private String role;

  //엔티티로 변환
  public Member toEntity() {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return Member.builder()
        .loginId(this.loginId)
        .realName(this.realName)
        .email(this.email)
        .password(passwordEncoder.encode(this.password))
        .role(MemberRole.MEMBER)
        .build();
  }
}
