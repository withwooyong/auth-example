package com.example.auth.security.util;

import com.example.auth.entity.Member;
import com.example.auth.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application.properties")
class JWTUtilTest {

    private JWTUtil jwtUtil;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach setUp..............");
        jwtUtil = new JWTUtil();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateToken() throws Exception {
        String email = "user30@google.com";
        String str = jwtUtil.generateToken(email);
        System.out.println(str);
    }

    @Test
    void generateToken2() throws Exception {
        String email = "user30@google.com";
        String str1 = jwtUtil.generateToken(email);
        System.out.println(str1);
        Optional<Member> members = memberRepository.findByEmail(email, false);
        Member member = members.get();
        String str = jwtUtil.generateToken(email, member);
        System.out.println(str);
    }

    @Test
    void validateAndExtract() throws Exception {
        String email = "user30@google.com";
        String str = jwtUtil.generateToken(email);
        Thread.sleep(5000);
        String resultEmail = jwtUtil.validateAndExtract(str);
        assertThat(resultEmail).isEqualTo(email);
        System.out.println(resultEmail);
    }
}