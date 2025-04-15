package com.ll.blog.domain.global.security.config;

import com.ll.blog.domain.jwt.JwtAccessDeniedHandler;
import com.ll.blog.domain.jwt.JwtAuthenticationEntryPoint;
import com.ll.blog.domain.jwt.JwtFilter;
import com.ll.blog.domain.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtProvider jwtProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
            .requestMatchers("/api/members/signIn",
                "/api/members/signUp",
                "/h2-console/**").permitAll()
            .requestMatchers("/api/members/me").authenticated()
            .anyRequest().permitAll()
        )

        .csrf((csrf) -> csrf
            .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"),
                new AntPathRequestMatcher("/**")))

        .headers((headers) -> headers
            .addHeaderWriter(new XFrameOptionsHeaderWriter(
                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
        // form login disable
        .formLogin(AbstractHttpConfigurer::disable)

        // logout disable
        .logout(AbstractHttpConfigurer::disable)

        // session management
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 미사용
        )
        // before filter
        .addFilterBefore(new JwtFilter(jwtProvider),
            UsernamePasswordAuthenticationFilter.class)

        // exception handler
        .exceptionHandling(conf -> conf
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
        );

    return http.build();
  }

  @Bean
  public static BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
