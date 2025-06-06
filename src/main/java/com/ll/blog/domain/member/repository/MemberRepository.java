package com.ll.blog.domain.member.repository;

import com.ll.blog.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  boolean existsByLoginId(String loginId);

  boolean existsByEmail(String email);

  Member findByLoginId(String loginId);
}
