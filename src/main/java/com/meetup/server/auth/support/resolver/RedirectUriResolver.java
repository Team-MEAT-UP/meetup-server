package com.meetup.server.auth.support.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedirectUriResolver {

    private final RedirectUriProperties redirectUriProperties;

    public String resolveSuccessRedirectUri(HttpServletRequest request) {
        String origin = request.getHeader("Origin");

        if (redirectUriProperties.domain().develop().equals(origin)) {
            return redirectUriProperties.success().develop();
        }
        return redirectUriProperties.success().local();
    }

    public String resolveFailureRedirectUri(HttpServletRequest request) {
        String origin = request.getHeader("Origin");

        if (redirectUriProperties.domain().develop().equals(origin)) {
            return redirectUriProperties.failure().develop();
        }
        return redirectUriProperties.failure().local();
    }
}
