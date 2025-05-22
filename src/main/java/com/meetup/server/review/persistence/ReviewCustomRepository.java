package com.meetup.server.review.persistence;

import com.meetup.server.startpoint.persistence.projection.EventHistoryProjection;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ReviewCustomRepository {
    Map<UUID, Boolean> findReviewsWrittenByUser(List<EventHistoryProjection> projections, Long userId);
}
