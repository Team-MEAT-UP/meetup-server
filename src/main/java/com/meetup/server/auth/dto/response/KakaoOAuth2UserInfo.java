package com.meetup.server.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

public record KakaoOAuth2UserInfo(
        String socialId,
        KakaoAccount kakaoAccount
) implements OAuth2UserInfo {

    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record KakaoAccount(
            KakaoUserProfile profile,
            String name,
            String email
    ) {
        public String getNickname() {
            return profile.nickname();
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public record KakaoUserProfile(
                String nickname,
                String profileImageUrl,
                String email
        ) {
        }
    }

    @Override
    public String getSocialId() {
        return socialId;
    }

    @Override
    public String getNickname() {
        return kakaoAccount.getNickname();
    }

    @Override
    public String getProfileImage() {
        return kakaoAccount.profile.profileImageUrl();
    }

    //kakao name은 앱 권한 신청은 기존 운영 서비스에서 이 정보가 회원가입에 사용되고 있음을 증빙해야 사용 가능
    //따라서 nickname으로 대체
    @Override
    public String getName() {
        return kakaoAccount.getNickname();
    }

    @Override
    public String getEmail() {
        return kakaoAccount.email();
    }
}
