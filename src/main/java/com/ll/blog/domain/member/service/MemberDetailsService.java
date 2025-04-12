package com.ll.blog.domain.member.service;

import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
    return new User(String.valueOf(member.getMemberId()), member.getPassword(),
        Collections.singleton(new SimpleGrantedAuthority(member.getRole().toString())));
  }
}
