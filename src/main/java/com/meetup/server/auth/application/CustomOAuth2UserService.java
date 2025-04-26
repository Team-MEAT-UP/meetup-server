package com.meetup.server.auth.application;

import com.meetup.server.auth.dto.CustomOAuth2User;
import com.meetup.server.auth.dto.response.OAuthAttributes;
import com.meetup.server.member.domain.Member;
import com.meetup.server.member.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // 소셜 로그인 식별값
        Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인 userInfo

        OAuthAttributes extractAttributes = OAuthAttributes.of(userNameAttributeName, attributes);

        Member createdMember = getOrGenerateMember(extractAttributes);

        // Default 객체 아닌, Custom 객체 생성 하여 반환
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("dummy_role")),    //TODO
                attributes,
                extractAttributes.nameAttributeKey(),
                createdMember.getEmail(),
                createdMember.getMemberId()
        );
    }

    private Member getOrGenerateMember(OAuthAttributes attributes) {
        log.info("attributes: {}", attributes);

        return memberRepository.findBySocialId(attributes.oauth2UserInfo().getSocialId())
                .orElseGet(() -> memberRepository.save(attributes.toEntity(attributes.oauth2UserInfo())));
    }
}
