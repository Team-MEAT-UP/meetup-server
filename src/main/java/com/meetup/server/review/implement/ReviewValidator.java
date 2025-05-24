package com.meetup.server.review.implement;

import com.meetup.server.place.domain.Place;
import com.meetup.server.review.exception.ReviewErrorType;
import com.meetup.server.review.exception.ReviewException;
import com.meetup.server.review.persistence.ReviewRepository;
import com.meetup.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

    private final ReviewRepository reviewRepository;

    public void validateReviewIsAlreadyWritten(Place place, User user) {
        if (reviewRepository.existsByPlaceAndUser(place, user)) {
            throw new ReviewException(ReviewErrorType.ALREADY_REVIEW_EXISTS);
        }
    }
}
