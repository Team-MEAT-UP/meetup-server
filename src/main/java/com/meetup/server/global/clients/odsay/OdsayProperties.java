package com.meetup.server.global.clients.odsay;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "odsay")
public record OdsayProperties(
        String secretKey,
        String baseUrl
) {
}
