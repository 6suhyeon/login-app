package com.example.login.service;

import com.example.login.dto.SignupRequest;
import com.example.login.entity.Member;
import com.example.login.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() {
        SignupRequest request = new SignupRequest();
        request.setUsername("testuser");
        request.setPassword("test1234");
        request.setName("테스트");
        request.setEmail("test@example.com");

        memberService.signup(request);

        Optional<Member> member = memberRepository.findByUsername("testuser");
        assertThat(member).isPresent();
        assertThat(member.get().getName()).isEqualTo("테스트");
        assertThat(member.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("회원가입 시 비밀번호가 BCrypt로 암호화된다")
    void signup_password_encrypted() {
        SignupRequest request = new SignupRequest();
        request.setUsername("testuser2");
        request.setPassword("test1234");
        request.setName("테스트2");
        request.setEmail("test2@example.com");

        memberService.signup(request);

        Member member = memberRepository.findByUsername("testuser2").orElseThrow();
        assertThat(member.getPassword()).isNotEqualTo("test1234");
        assertThat(member.getPassword()).startsWith("$2a$");
    }

    @Test
    @DisplayName("중복 아이디로 회원가입 시 예외 발생")
    void signup_duplicate_username() {
        SignupRequest request1 = new SignupRequest();
        request1.setUsername("duplicate");
        request1.setPassword("pass1234");
        request1.setName("유저1");
        request1.setEmail("user1@example.com");
        memberService.signup(request1);

        SignupRequest request2 = new SignupRequest();
        request2.setUsername("duplicate");
        request2.setPassword("pass5678");
        request2.setName("유저2");
        request2.setEmail("user2@example.com");

        assertThatThrownBy(() -> memberService.signup(request2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }
}
