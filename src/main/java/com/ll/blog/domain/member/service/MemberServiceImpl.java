package com.ll.blog.domain.member.service;

import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Transactional
    public void signUp(final MemberJoinRequest memberJoinRequest) {
        if (isCheckLoginId(memberJoinRequest.getLoginId())) {
            throw new IllegalArgumentException("아이디가 존재합니다.");
        }
        if (!memberJoinRequest.getPassword().equals(memberJoinRequest.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
        }
         memberRepository.save(memberJoinRequest.toEntity());
    }
    public boolean isCheckLoginId(final String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }
}
