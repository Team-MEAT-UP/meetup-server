package com.meetup.server.review.implement;

import com.meetup.server.place.domain.Place;
import com.meetup.server.review.domain.Review;
import com.meetup.server.review.persistence.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewReader {

    private final ReviewRepository reviewRepository;

    public List<Review> readAll(Place place) {
        return reviewRepository.findAllByPlace(place);
    }
}
