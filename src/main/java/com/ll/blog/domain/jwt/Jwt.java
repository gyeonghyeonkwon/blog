package com.ll.blog.domain.jwt;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Jwt {

  private String grantType;
  private String accessToken;
  private String refreshToken;
  private Date accessTokenExpiresIn;
}
