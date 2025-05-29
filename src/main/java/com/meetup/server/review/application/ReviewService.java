package com.meetup.server.review.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.implement.EventReader;
import com.meetup.server.place.domain.Place;
import com.meetup.server.place.implement.PlaceReader;
import com.meetup.server.review.dto.request.NonVisitedReviewRequest;
import com.meetup.server.review.dto.request.VisitedReviewRequest;
import com.meetup.server.review.implement.ReviewWriter;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final PlaceReader placeReader;
    private final UserReader userReader;
    private final ReviewWriter reviewWriter;
    private final EventReader eventReader;

    @Transactional
    public void createVisitedReview(UUID eventId, UUID placeId, Long userId, VisitedReviewRequest visitedReviewRequest) {
        Place place = placeReader.read(placeId);
        User user = userReader.read(userId);
        Event event = eventReader.read(eventId);

        reviewWriter.saveVisitedReview(event, place, user, visitedReviewRequest);
    }

    @Transactional
    public void createNonVisitedReview(UUID eventId, UUID placeId, Long userId, NonVisitedReviewRequest nonVisitedReviewRequest) {
        Place place = placeReader.read(placeId);
        User user = userReader.read(userId);
        Event event = eventReader.read(eventId);

        reviewWriter.saveNonVisitedReview(event, place, user, nonVisitedReviewRequest);
    }
}
