package com.meetup.server.auth.application.ext;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2.kakao")
public record KakaoAuthProperties(
        String clientId,
        String redirectUri,
        String clientSecret,
        String propertyKeys
) {
}
