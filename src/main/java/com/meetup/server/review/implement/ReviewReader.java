package com.meetup.server.review.implement;

import com.meetup.server.review.domain.Review;
import com.meetup.server.review.persistence.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReviewReader {

    private final ReviewRepository reviewRepository;

    public Optional<List<Review>> readAll(UUID placeId) {
        return reviewRepository.findAllByPlaceId(placeId);
    }
}
