package com.example.auth.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Getter
@Setter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {

    private String email;
    private String password;
    private String name;
    private boolean fromSocial;
    private Map<String, Object> attr;
    private String jwt;

    public AuthMemberDTO(String email, String username, String password, String jwt, boolean fromSocial,
                         Collection<? extends GrantedAuthority> authorities,
                         Map<String, Object> attr) {
        this(email, username, password, jwt, fromSocial, authorities);
        this.attr = attr;
    }

    public AuthMemberDTO(String email, String username, String password, String jwt, boolean fromSocial,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.name = username;
        this.email = email;
        this.password = password;
        this.fromSocial = fromSocial;
        this.jwt = jwt;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
