package com.meetup.server.auth.dto.response;

public interface OAuth2UserInfo {

    String getSocialId();
    String getNickname();
    String getProfileImage();
    String getName();
    String getEmail();
}
