package com.meetup.server.auth.dto.response;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public record JwtUserDetails(
        Long userId
) implements UserDetails {

    public static JwtUserDetails fromClaim(Claims claims) {
        Long userId = Long.valueOf(claims.getSubject());
        return new JwtUserDetails(userId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_USER");    //기본 권한 부여
    }

    @Override
    public String getPassword() {
        return null; // jwt 기반이므로 password 사용하지 않음
    }

    @Override
    public String getUsername() {
        return String.valueOf(userId);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
