package com.meetup.server.auth.support.handler;

import com.meetup.server.auth.dto.CustomOAuth2User;
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

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }

        String targetUrl = createRedirectUrlWithTokens(oAuth2User);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    //parameter로 access_token과 refresh_token을 추가한 URL 생성
    private String createRedirectUrlWithTokens(CustomOAuth2User user) {
        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken(user);

        return UriComponentsBuilder.fromUriString(successRedirectUri)
                .queryParam("access_token", accessToken)
                .queryParam("refresh_token", refreshToken)
                .build()
                .toUriString();
    }
}
