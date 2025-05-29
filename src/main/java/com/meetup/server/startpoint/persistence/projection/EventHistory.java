package com.meetup.server.startpoint.persistence.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventHistory(
        UUID eventId,
        UUID placeId,
        String subwayName,
        String placeName,
        LocalDateTime createdAt
) {}
