package com.meetup.server.startpoint.persistence.projection;

import java.util.UUID;

public record ParticipantProjection(
        UUID eventId,
        String profileImageUrl
) {
}
