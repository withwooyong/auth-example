package com.example.auth.controller;

import com.example.auth.security.dto.AuthMemberDTO;
import com.example.auth.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/sample/")
public class SampleController {

    private final JWTUtil jwtUtil;

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public String all() {
        log.debug("exAll........................");
        return "all";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/member")
    public String member(Model model, @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.debug("exMember........................");
        log.debug(authMemberDTO.toString());
        model.addAttribute("jwt", authMemberDTO.getJwt());
        return "member";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        log.debug("admin........................");
        return "admin";
    }

    @PreAuthorize("#authMemberDTO != null && #authMemberDTO.username eq \"user10@google.com\"")
    @GetMapping("/only")
    public String exMemberOnly(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.debug("only10 user...................");
        log.debug(authMemberDTO.toString());
        return "only";
    }
}
