package com.meetup.server.startpoint.persistence.projection;

import java.util.UUID;

public record ParticipantCountProjection(
        UUID eventId,
        Long count
) {}
