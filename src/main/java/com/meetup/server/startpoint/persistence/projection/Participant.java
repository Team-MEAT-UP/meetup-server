package com.meetup.server.startpoint.persistence.projection;

import java.util.UUID;

public record Participant(
        UUID eventId,
        String profileImageUrl
) {
}
