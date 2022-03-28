package com.example.auth.repository;

import com.example.auth.entity.Member;
import com.example.auth.entity.Note;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void insertDummies() {
        // 1 - 10 : USER
        // 11 - 20 : USER, MANAGER
        // 21 - 30 : USER, MANAGER, ADMIN
        IntStream.rangeClosed(1, 30).forEach(i -> {
            Note note = Note.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer(Member.builder().email("user" + i + "@google.com").build())
                    .build();
            noteRepository.save(note);
        });
    }

    @AfterEach
    void tearDown() {
    }

    @Test


    void getWithWriter() {
    }

    @Test
    void getList() {
        List<Note> result10 = noteRepository.getList("user10@google.com");
        result10.forEach(System.out::println);

        List<Note> result20 = noteRepository.getList("user20@google.com");
        result20.forEach(System.out::println);

        List<Note> result30 = noteRepository.getList("user30@google.com");
        result30.forEach(System.out::println);
    }

    @Test
    void getMember() {
        Optional<Member> member = Optional.of(memberRepository.findByEmail("user1@google.com", false).get());

        System.out.println(member.get().toString());
    }
}