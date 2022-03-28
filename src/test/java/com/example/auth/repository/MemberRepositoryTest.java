package com.example.auth.repository;

import com.example.auth.entity.Member;
import com.example.auth.entity.MemberRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
//        memberRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
//        memberRepository.deleteAll();
    }

    @Test
    public void insertDummies() {
        // 1 - 10 : USER
        // 11 - 20 : USER, MANAGER
        // 21 - 30 : USER, MANAGER, ADMIN
        IntStream.rangeClosed(1, 30).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@google.com")
                    .name("user" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();
            // default role
            member.addMemberRole(MemberRole.USER);
            if (i > 10)
                member.addMemberRole(MemberRole.MANAGER);
            if (i > 20)
                member.addMemberRole(MemberRole.ADMIN);
            memberRepository.save(member);
        });
    }

    @Test
    void findByEmail() {
        Optional<Member> result10 = memberRepository.findByEmail("user10@google.com", false);
        Optional<Member> result20 = memberRepository.findByEmail("user20@google.com", false);
        Optional<Member> result30 = memberRepository.findByEmail("user30@google.com", false);
        Member member10 = result10.get();
        Member member20 = result20.get();
        Member member30 = result30.get();
        System.out.println(member10);
        System.out.println(member20);
        System.out.println(member30);

    }
}