package com.example.login.config;

import com.example.login.entity.Member;
import com.example.login.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!memberRepository.existsByUsername("admin")) {
                Member admin = new Member();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin1234"));
                admin.setName("관리자");
                admin.setEmail("admin@example.com");
                admin.setRole(Member.Role.ADMIN);
                memberRepository.save(admin);
            }

            if (!memberRepository.existsByUsername("user")) {
                Member user = new Member();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user1234"));
                user.setName("테스트유저");
                user.setEmail("user@example.com");
                user.setRole(Member.Role.USER);
                memberRepository.save(user);
            }
        };
    }
}
