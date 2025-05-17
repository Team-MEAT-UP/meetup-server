package com.meetup.server.review.implement;

import com.meetup.server.review.domain.Review;
import com.meetup.server.review.persistence.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewReader {

    private final ReviewRepository reviewRepository;

    public Optional<List<Review>> readAll(Long placeId) {
        return reviewRepository.findAllByPlaceId(placeId);
    }
}
