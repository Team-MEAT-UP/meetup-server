package com.meetup.server.global.clients.google.place;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.place")
public record GooglePlaceProperties(
        String secretKey,
        String baseUrl
) {
}
