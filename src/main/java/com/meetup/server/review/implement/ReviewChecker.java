package com.meetup.server.review.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.place.domain.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewChecker {

    private final ReviewReader reviewReader;

    public boolean isReviewed(Event event, Long userId) {
        Place place = event.getPlace();
        if (place == null) {
            return false;
        }

        return reviewReader.readAll(place)
                .stream()
                .anyMatch(review -> review.isWrittenBy(userId));
    }
}
