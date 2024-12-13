package com.ll.blog.domain.member.entity;

import com.ll.blog.domain.global.jpa.config.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import javax.security.sasl.AuthorizeCallback;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;

  private String realName;

  @Column(unique = true)
  private String loginId;

  private String password;

  @Email
  @Column(unique = true)
  private String email;

  @Enumerated(value = EnumType.STRING)
  private MemberRole role;
}
