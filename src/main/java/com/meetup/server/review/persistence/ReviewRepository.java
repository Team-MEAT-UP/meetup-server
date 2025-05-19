package com.meetup.server.review.persistence;

import com.meetup.server.place.domain.Place;
import com.meetup.server.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByPlace(Place place);
}
