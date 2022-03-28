package com.example.auth.security.service;

import com.example.auth.entity.Member;
import com.example.auth.repository.MemberRepository;
import com.example.auth.security.dto.AuthMemberDTO;
import com.example.auth.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("username={}", username);
        Optional<Member> result = memberRepository.findByEmail(username, false);

        if (result.isEmpty())
            throw new UsernameNotFoundException("Check Email or Social");

        Member member = result.get();
        String jwt = new JWTUtil().generateToken("", member);
        log.debug("------------------------------------------------------------------------------");
        log.debug(member.toString());
        log.debug("############### 1");
        log.debug("jwt={}", jwt);
        log.debug("############### 2");

        return new AuthMemberDTO(member.getName(), member.getEmail(), member.getPassword(),
                jwt, member.isFromSocial(),
                member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet()));
    }
}
