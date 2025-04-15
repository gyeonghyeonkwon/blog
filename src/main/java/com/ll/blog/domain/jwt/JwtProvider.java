package com.ll.blog.domain.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {

  private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60; // 1시간
  private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
  private final SecretKey key; // JWT 서명을 위한 Key 객체 선언

  // application.yml에서 secret 값 가져와서 key에 저장
  public JwtProvider(@Value("${spring.jwt.secret_key}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  // Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
  public Jwt generateToken(Authentication authentication) {
    long currentDate = System.currentTimeMillis();
    // 권한 가져오기
    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    // Access Token 생성
    String accessToken = Jwts.builder()
        .subject(authentication.getName())
        .claim("auth", authorities)
        .issuedAt(new Date(currentDate))
        .expiration(new Date(currentDate + ACCESS_TOKEN_EXPIRATION_TIME))
        .signWith(this.key, SIG.HS256)
        .compact();

    // Refresh Token 생성
    String refreshToken = Jwts.builder()
        .expiration(new Date(currentDate + REFRESH_TOKEN_EXPIRATION_TIME))
        .signWith(this.key, SIG.HS256)
        .compact();

    return Jwt.builder()
        .grantType("Bearer")
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .accessTokenExpiresIn(new Date(currentDate + ACCESS_TOKEN_EXPIRATION_TIME)) //액세스토큰만료시간
        .build();
  }

  public Authentication getAuthentication(String accessToken) {
    // 토큰 복호화
    Claims claims = parseClaims(accessToken);

    if (claims.get("auth") == null) {
      throw new RuntimeException("권한 정보가 없는 토큰입니다.");
    }

    // 클레임에서 권한 정보 가져오기
    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get("auth").toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    // UserDetails 객체를 만들어서 Authentication 리턴
    UserDetails userDetails = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
  }

  public boolean isValidateToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(this.key) // 키 설정 방식은 기존과 동일
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
    }
    return false;
  }

  // accessToken
  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parser()
          .verifyWith(this.key) // 키 설정 방식은 기존과 동일
          .build()
          .parseSignedClaims(accessToken)
          .getPayload();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}

