package com.meetup.server.auth.jwt;

import com.meetup.server.auth.dto.response.JwtUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final JwtUserDetails jwtUserDetails;

    public JwtAuthenticationToken(JwtUserDetails jwtUserDetails) {
        super(jwtUserDetails.getAuthorities());
        this.jwtUserDetails = jwtUserDetails;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return jwtUserDetails;
    }
}
