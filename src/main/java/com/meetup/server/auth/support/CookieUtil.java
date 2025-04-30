package com.meetup.server.auth.support;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;


public class CookieUtil {

    public static void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {

        ResponseCookie refreshTokenCookie = ResponseCookie.from("Refresh-Token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Strict")   //GET 요청
                .build();

        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
    }

    public static String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Refresh-Token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
