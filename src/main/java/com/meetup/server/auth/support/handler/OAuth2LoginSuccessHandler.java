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

    @Value("${app.oauth2.successRedirectUri}")
    private String successRedirectUri;

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

        String targetUrl = createRedirectUrlWithTokens();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String createRedirectUrlWithTokens() {
        return UriComponentsBuilder.fromUriString(successRedirectUri)
                .build()
                .toUriString();
    }
}
