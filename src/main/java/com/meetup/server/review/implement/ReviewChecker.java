package com.meetup.server.review.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.recommend.domain.RecommendPlace;
import com.meetup.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewChecker {

    private final ReviewReader reviewReader;

    public boolean isReviewed(Event event, List<User> participants) {
        RecommendPlace recommendPlace = event.getRecommendPlace();
        if (recommendPlace == null || !recommendPlace.getEvent().getEventId().equals(event.getEventId())) {
            return false;
        }

        return reviewReader.readAll(recommendPlace.getRecommendId())
                .orElse(List.of())
                .stream()
                .filter(review -> review.getRecommendPlace().getRecommendId().equals(recommendPlace.getRecommendId()))
                .map(review -> review.getUser().getUserId())
                .anyMatch(reviewerId ->
                        participants.stream().anyMatch(user -> user.getUserId().equals(reviewerId)));
    }
}
