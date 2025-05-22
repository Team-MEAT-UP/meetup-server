package com.meetup.server.user.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserEventHistoryProjection(
        UUID eventId,
        UUID placeId,
        String subwayName,
        String placeName,
        LocalDateTime createdAt
) {}
