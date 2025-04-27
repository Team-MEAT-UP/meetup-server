package com.meetup.server.global.clients.kakao.mobility;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao.mobility")
public record KakaoMobilityProperties(
        String secretKey,
        String url
) {
}
