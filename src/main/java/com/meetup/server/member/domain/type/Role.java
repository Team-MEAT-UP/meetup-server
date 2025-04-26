package com.meetup.server.member.domain.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    ;

    private final String authority;

    public String getAuthority() {
        return authority;
    }
}
