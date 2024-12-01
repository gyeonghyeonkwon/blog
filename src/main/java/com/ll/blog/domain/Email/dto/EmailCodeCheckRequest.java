package com.ll.blog.domain.Email.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailCodeCheckRequest {

  @NotBlank
  private String email;

  @NotBlank
  private String verificationCode; //인증번호
}
