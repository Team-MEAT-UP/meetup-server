package com.meetup.server.review.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.place.domain.Place;
import com.meetup.server.review.domain.Review;
import com.meetup.server.review.persistence.ReviewRepository;
import com.meetup.server.user.dto.response.UserEventHistoryProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReviewChecker {

    private final ReviewReader reviewReader;
    private final ReviewRepository reviewRepository;

    public boolean isReviewed(Event event, Long userId) {
        Place place = event.getPlace();
        if (place == null) {
            return false;
        }

        return reviewReader.readAll(place)
                .stream()
                .anyMatch(review -> review.isWrittenBy(userId));
    }

    public Map<UUID, Boolean> isReviewed(List<UserEventHistoryProjection> projections, Long userId) {
        // eventId → placeId 맵 생성
        Map<UUID, UUID> eventPlaceMap = projections.stream()
                .collect(Collectors.toMap(UserEventHistoryProjection::eventId, UserEventHistoryProjection::placeId));

        List<UUID> placeIds = eventPlaceMap.values().stream().distinct().toList();

        // 해당 유저가 작성한 리뷰 중 placeId 리스트에 포함된 리뷰 조회
        List<Review> reviews = reviewRepository.findByPlaceIdsAndUserId(placeIds, userId);

        Set<UUID> reviewedPlaceIds = reviews.stream()
                .map(review -> review.getPlace().getId())
                .collect(Collectors.toSet());

        // eventId 별로 placeId가 유저 리뷰에 포함되는지 여부 맵으로 생성
        return eventPlaceMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> reviewedPlaceIds.contains(entry.getValue())
                ));
    }
}
