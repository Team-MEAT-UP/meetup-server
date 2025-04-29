package com.meetup.server.global.clients.kakao.local;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao.local")
public record KakaoLocalProperties(
        String secretKey,
        String baseUrl
) {
}
