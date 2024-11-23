package com.ll.blog.domain.member.dto;

import com.ll.blog.domain.member.entity.Member;
import com.ll.blog.domain.member.entity.MemberRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinRequest {

    @NotBlank(message = "본명을 입력해주세요.")
    private String name;

    @NotBlank(message = "로그인 아이디를 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인란을 입력해주세요.")
    private String passwordConfirm;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(
            regexp = "^\\d{3}-\\d{4}-\\d{4}$",
            message = "전화번호를 다시 확인해주세요.(ex. 010-1234-1234)"
    )
    private String phoneNumber;

    @NotBlank(message = "이메일은 필수항목입니다.")
    @Email
    private String email;

    public Member toEntity() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return Member.builder()
                .email(this.email)
                .name(this.name)
                .loginId(this.loginId)
                .password(passwordEncoder.encode(this.password))
                .phoneNumber(this.phoneNumber)
                .role(MemberRole.MEMBER)
                .build();
    }
}
