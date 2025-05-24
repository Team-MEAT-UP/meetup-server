package com.meetup.server.startpoint.persistence.projection;

import java.util.UUID;

public record ParticipantCount(
        UUID eventId,
        Long count
) {}
