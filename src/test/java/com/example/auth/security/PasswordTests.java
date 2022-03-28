package com.example.auth.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode() {
        String password = "1111";
        String encode = passwordEncoder.encode(password);
        System.out.println("encode : " + encode);
        boolean matchResult = passwordEncoder.matches(password, encode);
        System.out.println("match Result : " + matchResult);
    }
}
