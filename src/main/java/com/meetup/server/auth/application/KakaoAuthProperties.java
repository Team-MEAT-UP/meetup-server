package com.meetup.server.auth.application;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2.kakao")
public record KakaoAuthProperties(
        String clientId,
        String redirectUri,
        String clientSecret,
        String propertyKeys
) {
}
