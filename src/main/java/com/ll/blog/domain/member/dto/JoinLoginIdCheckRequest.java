package com.ll.blog.domain.member.dto;

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
public class JoinLoginIdCheckRequest {

  @NotBlank(message = "로그인 아이디를 입력해주세요.")
  @Size(min = 5 , max = 12)
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{5,12}$",
      message = "아이디는 영어 소문자와 숫자를 포함하여 5~12자리여야 합니다."
  )
  private String loginId;
}
