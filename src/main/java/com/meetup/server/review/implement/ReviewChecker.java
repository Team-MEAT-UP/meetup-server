package com.meetup.server.review.implement;

import com.meetup.server.startpoint.persistence.projection.EventHistoryProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ReviewChecker {

    private final ReviewReader reviewReader;

    public Map<UUID, Boolean> isReviewed(List<EventHistoryProjection> projections, Long userId) {
        return reviewReader.findReviewsWrittenByUser(projections, userId);
    }
}
