package com.meetup.server.auth.support;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieUtil {

    private final CookieProperties cookieProperties;

    private void setCommonCookie(HttpServletResponse response, String name, String value, int maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(cookieProperties.httpOnly())
                .secure(cookieProperties.secure())
                .path("/")
                .domain(cookieProperties.domain())
                .maxAge(maxAge)
                .sameSite(cookieProperties.sameSite())
                .build();

        response.addHeader(cookieProperties.setCookie(), cookie.toString());
    }

    public void setAccessTokenCookie(HttpServletResponse response, String accessToken) {
        setCommonCookie(response, cookieProperties.accessToken(), accessToken, cookieProperties.accessTokenMaxAge());
    }

    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        setCommonCookie(response, cookieProperties.refreshToken(), refreshToken, cookieProperties.refreshTokenMaxAge());
    }

    public void deleteRefreshTokenCookie(HttpServletResponse response) {
        setCommonCookie(response, cookieProperties.refreshToken(), null, 0);
    }

    public void deleteAccessTokenCookie(HttpServletResponse response) {
        setCommonCookie(response, cookieProperties.accessToken(), null, 0);
    }

    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieProperties.refreshToken())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public String getAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieProperties.accessToken())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
