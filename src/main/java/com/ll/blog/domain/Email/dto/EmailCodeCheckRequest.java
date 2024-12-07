package com.ll.blog.domain.Email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailCodeCheckRequest {

  @Email
  @NotBlank(message = "이메일을 입력해주세요.")
  private String email;

  @Size(min = 6 , message = "6자리의 숫자를 입력해주세요.")
  @Pattern(regexp = "^[1-9]*$" , message = "1~9 사이의 숫자를입력해주세요.")
  private String verificationCode; //인증번호
}
