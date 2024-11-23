package com.ll.blog.domain.member.service;

import com.ll.blog.domain.member.dto.MemberJoinRequest;
import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.entity.MemberRole;
import com.ll.blog.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@ExtendWith(MockitoExtension.class)
@Transactional
class MemberServiceTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void 회원가입테스트() {

        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
               .name("테스트유저")
               .password("1234")
               .passwordConfirm("1234")
               .email("kyanghyang12@naver.com")
               .loginId("kyanghyang12")
               .build();
        when(memberRepository.existsByLoginId(memberJoinRequest.getLoginId())).thenReturn(false); //로그인아이디 중복확인
        memberService.signUp(memberJoinRequest);
        verify(memberRepository , times(1)).save(any());
    }
}