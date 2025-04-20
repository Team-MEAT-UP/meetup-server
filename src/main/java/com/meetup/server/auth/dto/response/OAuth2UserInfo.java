package com.meetup.server.auth.dto.response;

public interface OAuth2UserInfo {

    String getSocialId(); //소셜 식별 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"
    String getNickname();
    String getProfileImage();
    String getName();
    String getEmail();
}
