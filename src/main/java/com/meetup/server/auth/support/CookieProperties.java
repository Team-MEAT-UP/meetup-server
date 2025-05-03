package com.meetup.server.auth.support;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cookie")
public record CookieProperties(
        String accessToken,
        String refreshToken,
        String setCookie
) {
}
