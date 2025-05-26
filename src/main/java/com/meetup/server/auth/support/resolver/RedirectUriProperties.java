package com.meetup.server.auth.support.resolver;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.oauth2.redirect-uri")
public record RedirectUriProperties(
        Endpoints success,
        Endpoints failure,
        Endpoints domain
) {
    public record Endpoints(
            String local,
            String develop
    ) {
    }
}
