package com.meetup.server.auth.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetup.server.member.domain.Member;
import lombok.Builder;

import java.util.Map;

/**
 * 각 소셜에서 받아오는 데이터가 다르므로 소셜별로 데이터를 받는 데이터를 분기 처리하는 DTO 클래스
 */
@Builder
public record OAuthAttributes(
        String nameAttributeKey,        //OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
        OAuth2UserInfo oauth2UserInfo   //소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)
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

    /**
     * of메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 소셜 타입별로 주입된 상태 OAuth2UserInfo에서 socialId(식별값),
     */
    public Member toEntity(OAuth2UserInfo oauth2UserInfo) {
        return Member.builder()
                .email(oauth2UserInfo.getEmail())
                .nickname(oauth2UserInfo.getNickname())
                .profileImage(oauth2UserInfo.getProfileImage())
                .socialId(oauth2UserInfo.getSocialId())
                .build();
    }
}
