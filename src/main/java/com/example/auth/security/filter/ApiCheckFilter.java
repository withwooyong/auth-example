package com.example.auth.security.filter;

import com.example.auth.security.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class ApiCheckFilter extends OncePerRequestFilter {
    private final AntPathMatcher antPathMatcher;
    private final String pattern;
    private final JWTUtil jwtUtil;

    public ApiCheckFilter(String pattern, JWTUtil jwtUtil) {
        this.antPathMatcher = new AntPathMatcher();
        this.pattern = pattern;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("REQUEST URI : " + request.getRequestURI());
        log.debug("pattern={}", pattern);
        log.debug("request.getRequestURI()={}", request.getRequestURI());

        if (antPathMatcher.match(pattern, request.getRequestURI())) {
            log.debug("ApiCheckFilter..........................................................");
            boolean checkHeader = checkAuthHeader(request);
            if (checkHeader) {
                filterChain.doFilter(request, response);
                return;
            } else {
                // Authroization 헤더 값 없이 날아온다면
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                // json 리턴 및 한글깨짐 수정.
                response.setContentType("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "FAIL CHECK API TOKEN";
                json.put("code", "403");
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.print(json);
                return;
            }
        }
        filterChain.doFilter(request, response); // 다음 필터 단계로 넘김
    }

    private boolean checkAuthHeader(HttpServletRequest request) {
        log.debug("request={}", request.toString());
        boolean checkResult = false;
        String authHeader = request.getHeader("Authorization");
        log.debug("authHeader={}", authHeader);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            log.debug("Authorization exist : " + authHeader);
            try {
                String email = jwtUtil.validateAndExtract(authHeader.substring(7));
                log.debug("validate result : " + email);
                checkResult = email.length() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return checkResult;
    }
}
