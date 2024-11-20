package com.ll.blog.domain.member.service;

import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface MemberService {
    void signUp(final MemberJoinRequest memberJoinRequest);
}
