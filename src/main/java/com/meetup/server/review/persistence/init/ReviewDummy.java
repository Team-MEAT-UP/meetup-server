package com.meetup.server.review.persistence.init;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.persistence.EventRepository;
import com.meetup.server.global.support.DummyDataInit;
import com.meetup.server.place.domain.Place;
import com.meetup.server.place.persistence.PlaceRepository;
import com.meetup.server.review.domain.Review;
import com.meetup.server.review.domain.VisitedReview;
import com.meetup.server.review.domain.type.VisitedTime;
import com.meetup.server.review.domain.value.PlaceScore;
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
    private final EventRepository eventRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (reviewRepository.count() > 0) {
            log.info("[Review]더미 데이터 존재");
        } else {
            List<Review> reviewList = new ArrayList<>();

            UUID placeId = UUID.fromString("0196e4a6-e812-74a7-9525-7ae7c5345490");
            Place place = placeRepository.findById(placeId).orElseThrow();
            UUID eventId = UUID.fromString("01971cac-d384-734e-ad79-ef8568129841");
            Event event = eventRepository.findById(eventId).orElseThrow();

            Review DUMMY_REVIEW_1 = Review.builder()
                    .event(event)
                    .place(place)
                    .user(userRepository.findById(1L).orElseThrow())
                    .isVisited(true)
                    .build();

            Review DUMMY_REVIEW_2 = Review.builder()
                    .event(event)
                    .place(place)
                    .user(userRepository.findById(2L).orElseThrow())
                    .isVisited(true)
                    .build();

            Review DUMMY_REVIEW_3 = Review.builder()
                    .event(event)
                    .place(place)
                    .user(userRepository.findById(4L).orElseThrow())
                    .isVisited(true)
                    .build();

            VisitedReview DUMMY_VISITED_REVIEW_1 = VisitedReview.builder()
                    .review(DUMMY_REVIEW_1)
                    .visitedTime(VisitedTime.MORNING)
                    .placeScore(PlaceScore.of(5, 4, 3))
                    .content("아침에 갔는데 너무 좋았어요")
                    .build();

            VisitedReview DUMMY_VISITED_REVIEW_2 = VisitedReview.builder()
                    .review(DUMMY_REVIEW_2)
                    .visitedTime(VisitedTime.LUNCH)
                    .placeScore(PlaceScore.of(5, 4, 3))
                    .content("점심에 갔는데 너무 좋았어요")
                    .build();

            VisitedReview DUMMY_VISITED_REVIEW_3 = VisitedReview.builder()
                    .review(DUMMY_REVIEW_3)
                    .visitedTime(VisitedTime.NIGHT)
                    .placeScore(PlaceScore.of(5, 4, 3))
                    .content("저녁에 갔는데 너무 좋았어요")
                    .build();

            reviewList.add(DUMMY_REVIEW_1);
            reviewList.add(DUMMY_REVIEW_2);
            reviewList.add(DUMMY_REVIEW_3);

            DUMMY_REVIEW_1.addVisitedReview(DUMMY_VISITED_REVIEW_1);
            DUMMY_REVIEW_2.addVisitedReview(DUMMY_VISITED_REVIEW_2);
            DUMMY_REVIEW_3.addVisitedReview(DUMMY_VISITED_REVIEW_3);

            reviewRepository.saveAll(reviewList);
        }
    }
}
