package com.meetup.server.member.application;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2.kakao")
public record KakaoProperties(
        String clientId,
        String redirectUri,
        String clientSecret
) {
}
