package com.meetup.server.auth.application;

import com.meetup.server.auth.dto.response.ReissueTokenResponse;
import com.meetup.server.auth.exception.AuthErrorType;
import com.meetup.server.auth.exception.AuthException;
import com.meetup.server.auth.support.CookieUtil;
import com.meetup.server.global.support.jwt.JwtTokenProvider;
import com.meetup.server.user.application.UserService;
import com.meetup.server.user.domain.User;
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
    private final UserService userService;

    public void logout(HttpServletResponse response) {
        cookieUtil.deleteAccessTokenCookie(response);
        cookieUtil.deleteRefreshTokenCookie(response);
    }

    public String createAccessTokenForUser(Long userId) {
        User user = userService.getUserById(userId);
        return jwtTokenProvider.createAccessToken(user);
    }

    public ReissueTokenResponse reIssueToken(HttpServletResponse response, String refreshToken) {

        refreshToken = resolveToken(refreshToken);

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new AuthException(AuthErrorType.INVALID_REFRESH_TOKEN);
        }

        Long userId = jwtTokenProvider.extractUserIdFromToken(refreshToken);
        User user = userService.getUserById(userId);

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user);

        cookieUtil.setRefreshTokenCookie(response, newRefreshToken);
        cookieUtil.setAccessTokenCookie(response, accessToken);

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
