package com.ll.blog.domain.member.entity;


import lombok.Getter;

@Getter
public enum MemberRole {

  MEMBER("MEMBER"),
  ADMIN("ADMIN");

  private final String value;

  MemberRole(String value) {
    this.value = value;
  }
}
