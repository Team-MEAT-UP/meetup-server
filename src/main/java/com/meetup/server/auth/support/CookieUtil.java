package com.meetup.server.auth.support;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieUtil {

    private final CookieProperties cookieProperties;

    private void setCommonCookie(HttpServletResponse response, CookieProperties.CookieSetting cookieSetting, String name, String value, int maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(cookieSetting.httpOnly())
                .secure(cookieSetting.secure())
                .path("/")
                .domain(cookieSetting.domain())
                .maxAge(maxAge)
                .sameSite(cookieSetting.sameSite())
                .build();

        response.addHeader(cookieProperties.setCookie(), cookie.toString());
    }

    private CookieProperties.CookieSetting resolveCookieSetting(HttpServletRequest request) {
        String origin = request.getHeader("Origin");
        log.info("Origin header: {}", origin);

        if (cookieProperties.develop().domain().equals(origin)) {
            log.info("[Cookie Setting] develop cookie");
            return cookieProperties.develop();
        }
        log.info("[Cookie Setting] local cookie");
        return cookieProperties.local();
    }

    public void setAccessTokenCookie(HttpServletRequest request, HttpServletResponse response, String accessToken) {
        CookieProperties.CookieSetting setting = resolveCookieSetting(request);
        setCommonCookie(response, setting, cookieProperties.accessToken(), accessToken, cookieProperties.accessTokenMaxAge());
    }

    public void setRefreshTokenCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        CookieProperties.CookieSetting setting = resolveCookieSetting(request);
        setCommonCookie(response, setting, cookieProperties.refreshToken(), refreshToken, cookieProperties.refreshTokenMaxAge());
    }

    public void deleteRefreshTokenCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieProperties.CookieSetting setting = resolveCookieSetting(request);
        setCommonCookie(response, setting, cookieProperties.refreshToken(), null, 0);
    }

    public void deleteAccessTokenCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieProperties.CookieSetting setting = resolveCookieSetting(request);
        setCommonCookie(response, setting, cookieProperties.accessToken(), null, 0);
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
