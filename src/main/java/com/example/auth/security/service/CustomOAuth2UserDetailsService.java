package com.example.auth.security.service;

import com.example.auth.entity.Member;
import com.example.auth.entity.MemberRole;
import com.example.auth.repository.MemberRepository;
import com.example.auth.security.dto.AuthMemberDTO;
import com.example.auth.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("loadUser start ---------------------------------------------");
        // org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest 객체
        String clientName = userRequest.getClientRegistration().getClientName();

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.debug("# oAuth2User.getAttribute start #");
        oAuth2User.getAttributes().forEach((k, v) -> {
            // sub, picture, email, email_verified 등 출력
            log.debug(k + " : " + v);
        });
        log.debug("# oAuth2User.getAttribute end #");

        String email = null;
        // Google 로그인 일 경우
        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        } else if (clientName.equals("naver")) {
            Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttribute("response");
            assert response != null;
            email = (String) response.get("email");
        } else if(clientName.equals("kakao")) {
            Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");
            assert response != null;
            email = (String) response.get("email");
        }
        log.debug("clientName={}", clientName);
        log.debug("email={}", email);

        Member member = saveSocialMember(email);
        String jwt = jwtUtil.generateToken("", member);
        log.debug("############### 1");
        log.debug("jwt={}", jwt);
        log.debug("############### 2");
        return new AuthMemberDTO(member.getName(), member.getEmail(), member.getPassword(),
                jwt, true,
                member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()), oAuth2User.getAttributes());
    }

    private Member saveSocialMember(String email) {
        log.debug("saveSocialMember email={}", email);
        // 동일한 이메일로 가입한 회원이 있는 경우에는 조회만
        Optional<Member> result = repository.findByEmail(email, true);
        if (result.isPresent()) {
            log.debug("동일한 이메일로 가입한 회원이 있는 경우에는 조회만");
            return result.get();
        }
        // 없다면 회원 추가, 이름은 이메일 주소
        log.debug("없다면 회원 추가, 이름은 이메일 주소");
        Member member = Member.builder().email(email).name(email).password(passwordEncoder.encode("1111")).fromSocial(true).build();
        member.addMemberRole(MemberRole.USER);
        repository.save(member);
        return member;
    }
}
