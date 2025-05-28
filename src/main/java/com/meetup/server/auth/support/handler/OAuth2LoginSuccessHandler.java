package com.meetup.server.auth.support.handler;

import com.meetup.server.auth.dto.CustomOAuth2User;
import com.meetup.server.auth.support.CookieUtil;
import com.meetup.server.global.support.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.oauth2.successRedirectUri}")
    private String successRedirectUri;

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
        log.info("[Redirect URI] - {}", createRedirectUrl(request));
        return UriComponentsBuilder.fromUriString(createRedirectUrl(request))
                .build()
                .toUriString();
    }

    private String createRedirectUrl(HttpServletRequest request) {
        String state = request.getParameter("state");
        String to = null;
        String eventId = null;

        if (state != null && !state.isBlank()) {
            String decodedState = java.net.URLDecoder.decode(state, java.nio.charset.StandardCharsets.UTF_8);

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
        }

        String redirectBase = successRedirectUri + "/oauth/kakao/callback";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(redirectBase);


        if (eventId != null && !eventId.isBlank()) {
            uriBuilder.queryParam("to", to);
            uriBuilder.queryParam("eventId", eventId);
        }

        return uriBuilder.build().toUriString();
    }
}
