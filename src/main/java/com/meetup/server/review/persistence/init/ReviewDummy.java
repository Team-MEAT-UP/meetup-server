package com.meetup.server.review.persistence.init;

import com.meetup.server.global.support.DummyDataInit;
import com.meetup.server.place.domain.Place;
import com.meetup.server.place.persistence.PlaceRepository;
import com.meetup.server.review.domain.Review;
import com.meetup.server.review.domain.type.VisitedTime;
import com.meetup.server.review.persistence.ReviewRepository;
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
    private final com.meetup.server.user.persistence.UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (reviewRepository.count() > 0) {
            log.info("[REVIEW]더미 데이터 존재");
        } else {
            List<Review> reviewList = new ArrayList<>();

            UUID placeId = UUID.fromString("0196e8ba-af20-7fb4-9102-8e86ca89e76e");
            Place place = placeRepository.findById(placeId).orElseThrow();

            Review DUMMY_REVIEW_1 = Review.builder()
                    .place(place)
                    .user(userRepository.findById(4L).orElseThrow())
                    .isVisited(true)
                    .visitedTime(VisitedTime.MORNING)
                    .content("공부에 집중하기 딱 좋은 느좋 카페입니다.")
                    .etcReason(null)
                    .build();

            reviewList.add(DUMMY_REVIEW_1);

            reviewRepository.saveAll(reviewList);
        }
    }
}
