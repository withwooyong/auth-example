package com.example.auth.security.util;

import com.example.auth.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
public class JWTUtil {

    private String secretKey = "abcdef12345678";

    // 1month
    private long expire = 60 * 24 * 30;

    public String generateToken(String content) throws Exception {
        log.debug("content={}", content);
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
                //.setExpiration(Date.from(ZonedDateTime.now().plusSeconds(1).toInstant()))
                .claim("sub", content)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8")).compact();
    }

    public String generateToken(String content, Member member) throws Exception {
        log.debug("content={}", content);

        Claims claims = Jwts.claims().setSubject(String.valueOf(member.getEmail()));
        claims.put("name", member.getName());
        claims.put("roles", member.getRoleSet());
        claims.put("email", member.getEmail());
        claims.put("createdTime", System.nanoTime());
        claims.put("content", "내용추가..");

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
                //.setExpiration(Date.from(ZonedDateTime.now().plusSeconds(1).toInstant()))
//                .claim("sub", content)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes("UTF-8")).compact();
    }

    public String validateAndExtract(String tokenStr) throws Exception {
        log.debug("tokenStr={}", tokenStr);
        String contentValue = null;
        try {
            DefaultJws defaultJws = (DefaultJws) Jwts.parser().setSigningKey(secretKey.getBytes("UTF-8")).parseClaimsJws(tokenStr);
            log.debug("defaultJws={}", defaultJws);
            log.debug("defaultJws class={}", defaultJws.getBody().getClass());
            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();
            log.debug("----------------------------------------------");
            contentValue = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            contentValue = null;
        }
        return contentValue;
    }
}
