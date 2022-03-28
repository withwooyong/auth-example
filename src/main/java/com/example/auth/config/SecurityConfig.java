package com.example.auth.config;

import com.example.auth.security.filter.ApiCheckFilter;
import com.example.auth.security.filter.ApiLoginFilter;
import com.example.auth.security.handler.ApiLoginFailHandler;
import com.example.auth.security.handler.LoginSuccessHandler;
import com.example.auth.security.service.CustomUserDetailsService;
import com.example.auth.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter("/notes/**/*", jwtUtil());
    }

    @Bean
    public JWTUtil jwtUtil() {
        return new JWTUtil();
    }

    @Bean
    public ApiLoginFilter apiLoginFilter() throws Exception {
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login", jwtUtil());
        apiLoginFilter.setAuthenticationManager(authenticationManager());
        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());
        return apiLoginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.formLogin(); // 인가/인증 문제 시 로그인 화면 표출
        http.csrf().disable(); // csrf 토큰 생성 비활성화 -> 외부에서 REST 방식으로 이용하는 보안 설정 다루기 위해
        http.logout(); // 로그아웃
//        http.rememberMe().tokenValiditySeconds(60 * 60 * 7).userDetailsService(userDetailsService); // rememberMe 유효기간 7일
        http.oauth2Login().successHandler(successHandler()); // oauth login
        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);  // api 권한 체크 필터
        http.addFilterBefore(apiLoginFilter(), UsernamePasswordAuthenticationFilter.class);  // jwt 토큰 발급 필터
    }

    @Bean
    public LoginSuccessHandler successHandler() {
        return new LoginSuccessHandler(passwordEncoder());
    }
}
