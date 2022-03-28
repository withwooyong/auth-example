package com.example.auth.security.handler;

import com.example.auth.security.dto.AuthMemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final PasswordEncoder passwordEncoder;

    public LoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("--------------------------------------");
        log.debug("onAuthenticationSuccess ");

        AuthMemberDTO authMember = (AuthMemberDTO) authentication.getPrincipal();
        boolean fromSocial = authMember.isFromSocial();
        log.debug("########## Need Modify Member ? " + fromSocial);
        boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());

        log.debug("authMember={}", authMember);
        if (fromSocial && passwordResult) {
            redirectStrategy.sendRedirect(request, response, "/sample/member");
//            redirectStrategy.sendRedirect(request, response, "/member/modify?form=social");
        }
    }
}
