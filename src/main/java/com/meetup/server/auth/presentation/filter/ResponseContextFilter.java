package com.meetup.server.auth.presentation.filter;

import com.meetup.server.auth.application.AuthService;
import com.meetup.server.auth.dto.response.ReissueTokenResponse;
import com.meetup.server.auth.support.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseContextFilter extends OncePerRequestFilter {

    private final AuthService authService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String refreshToken = CookieUtil.getRefreshTokenFromCookie(request);

        if (refreshToken != null) {
            ReissueTokenResponse reissueTokenResponse = authService.reIssueToken(refreshToken);
            setResponseCookies(response, reissueTokenResponse);
        }

        filterChain.doFilter(request, response);
    }

    private void setResponseCookies(HttpServletResponse response, ReissueTokenResponse reissueTokenResponse) {

        CookieUtil.setRefreshTokenCookie(response, reissueTokenResponse.refreshToken());
    }
}
