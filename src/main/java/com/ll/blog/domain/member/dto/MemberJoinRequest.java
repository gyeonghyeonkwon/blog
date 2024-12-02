package com.ll.blog.domain.member.dto;

import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.entity.MemberRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinRequest {

  @NotBlank(message = "본명을 입력해주세요.")
  private String realName;

  @NotBlank(message = "로그인 아이디를 입력해주세요.")
  private String loginId;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  private String password;

  @NotBlank(message = "비밀번호 확인란을 입력해주세요.")
  private String passwordConfirm;

  @NotBlank(message = "이메일은 필수항목입니다.")
  @Email
  private String email;

  public Member toEntity() {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return Member.builder()
        .email(this.email)
        .realName(this.realName)
        .loginId(this.loginId)
        .password(passwordEncoder.encode(this.password))
        .role(MemberRole.MEMBER)
        .build();
  }
}
