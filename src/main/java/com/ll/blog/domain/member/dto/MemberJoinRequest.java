package com.ll.blog.domain.member.dto;

import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.entity.MemberRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinRequest {

  @NotBlank(message = "본명을 입력해주세요.")
  @Size(min = 2, max = 4 , message = "본명은 4자리까지 입력가능합니다.")
  @Pattern(regexp = "^[가-힣]*$" , message = "본명은 한글만 작성합니다.")
  private String realName;

  @NotBlank(message = "로그인 아이디를 입력해주세요.")
  @Size(min = 5 , max = 12)
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{5,12}$",
      message = "아이디는 영어 소문자와 숫자를 포함하여 5~12자리여야 합니다."
  )  private String loginId;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  private String password;

  @NotBlank(message = "비밀번호 확인란을 입력해주세요.")
  private String passwordConfirm;

  @NotBlank(message = "이메일은 필수항목입니다.")
  @Email
  @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$" , message = "정확한 이메일을 입력해주세요.")
  private String email;

  @NotBlank(message = "인증코드를 입력해주세요.")
  @Size(min = 6 , message = "6자리의 숫자를 입력해주세요.")
  @Pattern(regexp = "^[1-9]*$" , message = "1~9 사이의 숫자를입력해주세요.")
  private String verificationCode; //인증코드

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
