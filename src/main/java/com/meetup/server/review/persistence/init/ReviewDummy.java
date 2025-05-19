package com.meetup.server.review.persistence.init;

import com.meetup.server.global.support.DummyDataInit;
import com.meetup.server.place.domain.Place;
import com.meetup.server.place.persistence.PlaceRepository;
import com.meetup.server.review.domain.Review;
import com.meetup.server.review.domain.VisitedReview;
import com.meetup.server.review.domain.type.VisitedTime;
import com.meetup.server.review.domain.value.PlaceRating;
import com.meetup.server.review.persistence.ReviewRepository;
import com.meetup.server.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Profile("local")
@Order(3)
@DummyDataInit
public class ReviewDummy implements ApplicationRunner {

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (reviewRepository.count() > 0) {
            log.info("[REVIEW]더미 데이터 존재");
        } else {
            List<Review> reviewList = new ArrayList<>();

            UUID placeId = UUID.fromString("0196e96a-3993-7035-8caf-0436872b6644");
            Place place = placeRepository.findById(placeId).orElseThrow();

            Review DUMMY_REVIEW_1 = Review.builder()
                    .place(place)
                    .user(userRepository.findById(4L).orElseThrow())
                    .isVisited(true)
                    .build();

            VisitedReview DUMMY_VISITED_REVIEW_1 = VisitedReview.builder()
                    .review(DUMMY_REVIEW_1)
                    .visitedTime(VisitedTime.MORNING)
                    .placeRating(PlaceRating.of(5, 4, 3))
                    .content("아침에 갔는데 너무 좋았어요")
                    .build();

            reviewList.add(DUMMY_REVIEW_1);
            DUMMY_REVIEW_1.addVisitedReview(DUMMY_VISITED_REVIEW_1);

            reviewRepository.saveAll(reviewList);
        }
    }
}
