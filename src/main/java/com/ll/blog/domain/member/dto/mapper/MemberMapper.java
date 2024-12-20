package com.ll.blog.domain.member.dto.mapper;

import com.ll.blog.domain.member.dto.MemberJoinCommand;
import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

  @Mapping(target = "role", ignore = true)
  MemberJoinCommand toCommand(MemberJoinRequest memberJoinRequest);

  @Mapping(target = "memberId", ignore = true)
  @Mapping(target = "role", defaultValue = "MEMBER")
  Member toEntity(MemberJoinCommand memberJoinCommand);
}
