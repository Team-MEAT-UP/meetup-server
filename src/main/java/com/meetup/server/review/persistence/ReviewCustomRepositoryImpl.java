package com.meetup.server.review.persistence;

import com.meetup.server.startpoint.persistence.projection.EventHistoryProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.meetup.server.review.domain.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Map<UUID, Boolean> findReviewsWrittenByUser(List<EventHistoryProjection> projections, Long userId) {
        Map<UUID, UUID> eventToPlaceMap = projections.stream()
                .filter(projection -> projection.placeId() != null)
                .collect(Collectors.toMap(EventHistoryProjection::eventId, EventHistoryProjection::placeId));

        List<UUID> placeIds = new ArrayList<>(new HashSet<>(eventToPlaceMap.values()));

        Set<UUID> reviewedPlaceIds = new HashSet<>(
                jpaQueryFactory
                        .select(review.place.id)
                        .from(review)
                        .where(
                                review.place.id.in(placeIds),
                                review.user.userId.eq(userId)
                        )
                        .fetch()
        );

        return eventToPlaceMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> reviewedPlaceIds.contains(entry.getValue())
                ));
    }
}
