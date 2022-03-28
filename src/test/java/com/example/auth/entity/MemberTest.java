package com.example.auth.entity;

import com.example.auth.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach setUp =============");
//        memberRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void baseTimeEntity() {
        //given
        LocalDateTime now = LocalDateTime.of(2022, 3, 17, 0, 0, 0);

        Member member1 = Member.builder()
                .name("user")
                .email("user@google.com")
                .password(passwordEncoder.encode("1111"))
                .fromSocial(false)
                .build();
        memberRepository.save(member1);
        //when
        Optional<Member> members = memberRepository.findByEmail("user@google.com", false);
        //then
        Member member = members.get();
        System.out.println(">>>>> createDate = "+member.getCreatedDate() + ", modifiedDate = "+member.getModifiedDate());
//        assertThat(member.getCreatedDate()).isAfter(now);
//        assertThat(member.getModifiedDate()).isAfter(now);
    }

    @Test
    void getRegDate() {
    }

    @Test
    void getModDate() {
    }

    @Test
    void addMemberRole() {
    }
}