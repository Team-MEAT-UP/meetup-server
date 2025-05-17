package com.meetup.server.review.persistence;

import com.meetup.server.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.recommendPlace.recommendId = :placeId")
    Optional<List<Review>> findAllByPlaceId(@Param("placeId") Long placeId);
}

