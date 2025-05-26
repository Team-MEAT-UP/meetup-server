package com.meetup.server.auth.support.handler;

import com.meetup.server.auth.dto.CustomOAuth2User;
import com.meetup.server.auth.support.CookieUtil;
import com.meetup.server.auth.support.resolver.RedirectUriResolver;
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

    private final RedirectUriResolver redirectUriResolver;
    private final JwtTokenProvider tokenProvider;
    private final CookieUtil cookieUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String accessToken = tokenProvider.createAccessToken(oAuth2User);
        String refreshToken = tokenProvider.createRefreshToken(oAuth2User);

        cookieUtil.setAccessTokenCookie(request, response, accessToken);
        cookieUtil.setRefreshTokenCookie(request, response, refreshToken);

        String targetUrl = createRedirectUrlWithTokens(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String createRedirectUrlWithTokens(HttpServletRequest request) {
        String successRedirectUri = redirectUriResolver.resolveSuccessRedirectUri(request);

        return UriComponentsBuilder.fromUriString(successRedirectUri)
                .build()
                .toUriString();
    }
}
