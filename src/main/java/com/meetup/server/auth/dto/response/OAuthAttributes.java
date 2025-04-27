package com.meetup.server.auth.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetup.server.member.domain.Member;
import com.meetup.server.member.domain.type.Role;
import lombok.Builder;

import java.util.Map;

@Builder
public record OAuthAttributes(
        String nameAttributeKey,        //OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
        OAuth2UserInfo oauth2UserInfo   //소셜 타입별 로그인 유저 정보
) {

    public static OAuthAttributes of(
            String userNameAttributeName, Map<String, Object> attributes
    ) {
        return ofKakao(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoOAuth2UserInfo.KakaoAccount kakaoAccount = objectMapper.convertValue(attributes.get("kakao_account"), KakaoOAuth2UserInfo.KakaoAccount.class);

        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes.get("id").toString(), kakaoAccount))
                .build();
    }

    public Member toEntity(OAuth2UserInfo oauth2UserInfo) {

        return Member.builder()
                .email(oauth2UserInfo.getEmail())
                .nickname(oauth2UserInfo.getNickname())
                .profileImage(oauth2UserInfo.getProfileImage())
                .socialId(oauth2UserInfo.getSocialId())
                .role(Role.MEMBER)
                .build();
    }
}
