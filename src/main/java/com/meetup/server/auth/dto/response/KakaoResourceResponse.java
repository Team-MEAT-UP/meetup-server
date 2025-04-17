package com.meetup.server.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoResourceResponse(
        Long id,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record KakaoAccount(
            Profile profile,
            String email
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Profile(
                @JsonProperty("thumbnail_image_url") String userProfileImage,
                @JsonProperty("is_default_image") String defaultProfileImage,
                String nickname
        ) {
        }
    }
}
