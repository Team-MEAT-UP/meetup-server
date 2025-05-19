package com.meetup.server.review.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.place.domain.Place;
import com.meetup.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewChecker {

    private final ReviewReader reviewReader;

    public boolean isReviewed(Event event, List<User> participants) {
        Place place = event.getPlace();
        if (place == null) {
            return false;
        }

        return reviewReader.readAll(place)
                .stream()
                .anyMatch(review ->
                        participants.stream().anyMatch(review::isWrittenBy));
    }
}
