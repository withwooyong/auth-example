package com.example.auth.security.filter;

import com.example.auth.security.dto.AuthMemberDTO;
import com.example.auth.security.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final JWTUtil jwtUtil;

    public ApiLoginFilter(String defaultFilterProcessesUrl, JWTUtil jwtUtil) {
        super(defaultFilterProcessesUrl);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.debug("-----------------ApiLoginFilter---------------------");
        log.debug("attemptAuthentication");
        String email = request.getParameter("email");
        String pw = request.getParameter("pw");
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, pw);
        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //super.successfulAuthentication(request, response, chain, authResult);
        log.debug("-------------------------------ApiLoginFilter-------------------------------");
        log.debug("successfulAuthentication : " + authResult);
        log.debug(authResult.getPrincipal().toString());

        //email address
        String email = ((AuthMemberDTO) authResult.getPrincipal()).getUsername();
        String token = null;

        try {
            token = jwtUtil.generateToken(email);
            response.setContentType("text/plain");
            response.getOutputStream().write(token.getBytes());

            log.debug(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
