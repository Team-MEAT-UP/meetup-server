package com.meetup.server.event.dto.request;

import java.util.UUID;

public record RouteRequest(
        UUID eventId,
        UUID id,
        boolean isTransit

) {
}
