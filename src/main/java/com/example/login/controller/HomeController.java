package com.example.login.controller;

import com.example.login.entity.Member;
import com.example.login.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
public class HomeController {

    private final MemberRepository memberRepository;

    public HomeController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails userDetails,
                       HttpSession session, Model model) {
        Member member = memberRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        List<Member> members = memberRepository.findAll();

        model.addAttribute("member", member);
        model.addAttribute("members", members);
        model.addAttribute("sessionId", session.getId());
        model.addAttribute("sessionCreated",
                LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(session.getCreationTime()),
                        ZoneId.systemDefault()));
        model.addAttribute("sessionTimeout", session.getMaxInactiveInterval() / 60);

        return "home";
    }
}
