package com.meetup.server.auth.support.handler;

import com.meetup.server.auth.dto.CustomOAuth2User;
import com.meetup.server.auth.support.CookieUtil;
import com.meetup.server.global.support.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final CookieUtil cookieUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String accessToken = tokenProvider.createAccessToken(oAuth2User);
        String refreshToken = tokenProvider.createRefreshToken(oAuth2User);

        cookieUtil.setAccessTokenCookie(response, accessToken);
        cookieUtil.setRefreshTokenCookie(response, refreshToken);

        String targetUrl = createRedirectUrlWithTokens(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String createRedirectUrlWithTokens(HttpServletRequest request) {
        log.info("[Redriect URI] - {}", createRedirectUrl(request));
        return UriComponentsBuilder.fromUriString(createRedirectUrl(request))
                .build()
                .toUriString();
    }

    private String createRedirectUrl(HttpServletRequest request) {
        String state = request.getParameter("state");
        String decodedState = java.net.URLDecoder.decode(state, java.nio.charset.StandardCharsets.UTF_8);

        String to = null;
        String eventId = null;

        for (String param : decodedState.split("&")) {
            String[] keyValue = param.split("=", 2);
            if (keyValue.length == 2) {
                if ("to".equals(keyValue[0])) {
                    to = keyValue[1];
                } else if ("eventId".equals(keyValue[0])) {
                    eventId = keyValue[1];
                }
            }
        }

        if (to == null) {
            to = "http://localhost:5173/history"; // 원하는 fallback URL
        }

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(to);

        if (eventId != null) {
            uriBuilder.queryParam("eventId", eventId);
        }

        return uriBuilder.build().toUriString();
    }
}
