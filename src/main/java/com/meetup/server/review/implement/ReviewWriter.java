package com.meetup.server.review.implement;

import com.meetup.server.place.domain.Place;
import com.meetup.server.review.domain.Review;
import com.meetup.server.review.domain.VisitedReview;
import com.meetup.server.review.domain.type.VisitedTime;
import com.meetup.server.review.domain.value.PlaceScore;
import com.meetup.server.review.persistence.ReviewRepository;
import com.meetup.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReviewWriter {

    private final ReviewRepository reviewRepository;
    private final ReviewValidator reviewValidator;

    @Transactional
    public void saveVisitedReview(Place place, User user, VisitedTime visitedTime, int socket, int seat, int quiet, String content) {
        reviewValidator.validateReviewIsAlreadyWritten(place, user);

        Review review = createReview(place, user, true);

        VisitedReview visitedReview = VisitedReview.builder()
                .review(review)
                .visitedTime(visitedTime)
                .placeScore(PlaceScore.of(socket, seat, quiet))
                .content(content)
                .build();

        review.addVisitedReview(visitedReview);
        reviewRepository.save(review);
    }

    private Review createReview(Place place, User user, boolean isVisited) {
        return Review.builder()
                .place(place)
                .user(user)
                .isVisited(isVisited)
                .build();
    }
}
