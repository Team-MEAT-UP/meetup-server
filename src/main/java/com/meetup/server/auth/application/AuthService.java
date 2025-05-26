package com.meetup.server.auth.application;

import com.meetup.server.auth.dto.response.ReissueTokenResponse;
import com.meetup.server.auth.exception.AuthErrorType;
import com.meetup.server.auth.exception.AuthException;
import com.meetup.server.auth.support.CookieUtil;
import com.meetup.server.global.support.jwt.JwtTokenProvider;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.implement.UserReader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CookieUtil cookieUtil;
    private final UserReader userReader;

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        cookieUtil.deleteAccessTokenCookie(request, response);
        cookieUtil.deleteRefreshTokenCookie(request, response);
    }

    public String createAccessTokenForUser(Long userId) {
        User user = userReader.read(userId);
        return jwtTokenProvider.createAccessToken(user);
    }

    public ReissueTokenResponse reIssueToken(HttpServletRequest request, HttpServletResponse response, String refreshToken) {

        refreshToken = resolveToken(refreshToken);

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new AuthException(AuthErrorType.INVALID_REFRESH_TOKEN);
        }

        Long userId = jwtTokenProvider.extractUserIdFromToken(refreshToken);
        User user = userReader.read(userId);

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user);

        cookieUtil.setRefreshTokenCookie(request, response, newRefreshToken);
        cookieUtil.setAccessTokenCookie(request, response, accessToken);

        return ReissueTokenResponse.from(accessToken, newRefreshToken);
    }

    private String resolveToken(String token) {
        log.info("accessToken check: {}", token);
        log.info("resolveToken check: {}", token);

        if (token == null) {
            throw new AuthException(AuthErrorType.INVALID_REFRESH_TOKEN);
        }
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return token;
    }
}
