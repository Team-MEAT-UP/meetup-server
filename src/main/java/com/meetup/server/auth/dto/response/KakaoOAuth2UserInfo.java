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

    @Override
    public String getName() {
        return kakaoAccount.getNickname();
    }

    @Override
    public String getEmail() {
        return kakaoAccount.email();
    }
}
