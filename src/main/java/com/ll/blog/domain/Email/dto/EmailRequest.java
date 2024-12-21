package com.ll.blog.domain.Email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

  @Email
  @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "정확한 이메일을 입력해주세요.")
  @NotBlank(message = "이메일을 입력해주세요.")
  private String email;

  public EmailCommand toCommand() {
    return EmailCommand.builder()
        .email(this.email)
        .build();
  }
}
