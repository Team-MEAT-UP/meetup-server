package com.meetup.server.auth.presentation.filter;

import com.meetup.server.auth.support.AuthenticationUtil;
import com.meetup.server.auth.support.CookieUtil;
import com.meetup.server.global.support.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationUtil authenticationUtil;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String token = cookieUtil.getAccessTokenFromCookie(request);
        log.info("Success Access Token With Cookie");

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            authenticationUtil.setAuthenticationFromRequest(request, token);
        }

        filterChain.doFilter(request, response);
    }
}
