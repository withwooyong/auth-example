//package com.example.auth.config;
//
//import com.example.auth.security.filter.ApiCheckFilter;
//import com.example.auth.security.filter.ApiLoginFilter;
//import com.example.auth.security.handler.ApiLoginFailHandler;
//import com.example.auth.security.handler.LoginSuccessHandler;
//import com.example.auth.security.service.CustomUserDetailsService;
//import com.example.auth.security.util.JWTUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@RequiredArgsConstructor
//@Configuration
//@Slf4j
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//public class SecurityConfig2 extends WebSecurityConfigurerAdapter {
//
//    private final CustomUserDetailsService userDetailsService;
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public ApiCheckFilter apiCheckFilter() {
//        return new ApiCheckFilter("/notes/**/*", jwtUtil());
//    }
//
//    @Bean
//    public JWTUtil jwtUtil() {
//        return new JWTUtil();
//    }
//
//    @Bean
//    public ApiLoginFilter apiLoginFilter() throws Exception {
//        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login", jwtUtil());
//        apiLoginFilter.setAuthenticationManager(authenticationManager());
//        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());
//        return apiLoginFilter;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/notes/**").permitAll();
////        http.authorizeRequests().antMatchers("/").permitAll().and()
////                .authorizeRequests().antMatchers("/console/**").permitAll();
//        http.headers().frameOptions().disable();
//
//        http.formLogin(); // ??????/?????? ?????? ??? ????????? ?????? ??????
//        http.csrf().disable(); // csrf ?????? ?????? ???????????? -> ???????????? REST ???????????? ???????????? ?????? ?????? ????????? ??????
//        http.logout(); // ????????????
////        http.rememberMe().tokenValiditySeconds(60 * 60 * 7).userDetailsService(userDetailsService); // rememberMe ???????????? 7???
//
//        http.oauth2Login().successHandler(successHandler()); // google login
//        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(apiLoginFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Bean
//    public LoginSuccessHandler successHandler() {
//        return new LoginSuccessHandler(passwordEncoder());
//    }
//
//    /*
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        // ????????? ?????? user2
//        auth.inMemoryAuthentication().withUser("user2")
//                .password("$2a$10$L9IC1R4hU0ibrW.8JsjUOuTkwnFV0fJw/UaFFItOzhsWaEVKBklwm").roles("USER");
//    }*/
//}
