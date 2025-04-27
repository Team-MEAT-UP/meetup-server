package com.meetup.server.auth.dto.response;

import com.meetup.server.member.domain.type.Role;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record JwtUserDetails(
        Long userId,
        Collection<? extends GrantedAuthority> authorities
) implements UserDetails {

    public static JwtUserDetails fromClaim(Claims claims) {
        Long userId = Long.valueOf(claims.getSubject());
        Collection<? extends GrantedAuthority> authorities = List.of(
                Role.MEMBER::getAuthority
        );
        return new JwtUserDetails(userId, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
