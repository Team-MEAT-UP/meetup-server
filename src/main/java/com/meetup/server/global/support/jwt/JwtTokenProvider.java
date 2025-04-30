package com.meetup.server.global.support.jwt;

import com.meetup.server.auth.dto.CustomOAuth2User;
import com.meetup.server.auth.dto.response.JwtUserDetails;
import com.meetup.server.user.application.UserService;
import com.meetup.server.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final UserService userService;
    private final JwtProperties jwtProperties;
    private Key key;

    @PostConstruct
    public void setKey() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes());
    }

    public String createAccessToken(Object user) {
        return createToken(user, jwtProperties.accessTokenExpiration());
    }

    public String createRefreshToken(Object user) {
        return createToken(user, jwtProperties.refreshTokenExpiration());
    }

    private String createToken(Object user, long expiration) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + expiration);

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(new Date(now))
                .setExpiration(validity)
                .setIssuer(jwtProperties.issuer())
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS512);

            if (user instanceof CustomOAuth2User oAuth2User) {
                builder.setSubject(oAuth2User.getUserId().toString());
            } else if (user instanceof User normalUser) {
                builder.setSubject(normalUser.getUserId().toString());
            } else {
                throw new IllegalArgumentException("Unsupported user type: " + user.getClass().getName());
            }

            return builder.compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            log.info("login user: {}", claims.getBody().getSubject());
            return claims.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            log.error("Token validation error: ", e);
            return false;
        }
    }

    public JwtUserDetails getJwtUserDetails(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return JwtUserDetails.fromClaim(claims);
    }

    public User extractUserIdFromToken(String token) {
        Long id = Long.parseLong(Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());

        return userService.getUserById(id);
    }
}
