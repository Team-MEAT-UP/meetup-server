package com.meetup.server.global.clients.odsay;

import lombok.Builder;

@Builder
public record OdsayTransitRouteSearchRequest(
        String sx,
        String sy,
        String ex,
        String ey,
        Integer opt,
        Integer searchType,
        Integer searchPathType
) {
}
