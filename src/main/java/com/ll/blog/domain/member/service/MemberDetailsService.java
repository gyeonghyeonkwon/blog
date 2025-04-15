package com.ll.blog.domain.member.service;

import com.ll.blog.domain.global.security.CustomUserDetails;
import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    Member member = memberRepository.findByLoginId(loginId);
    if (member == null) {
      throw new UsernameNotFoundException(loginId + " -> 아이디를 찾을 수 없습니다.");
    }
    return new CustomUserDetails(member);
  }
}
