package com.meetup.server.review.persistence;

import com.meetup.server.place.domain.Place;
import com.meetup.server.review.domain.Review;
import com.meetup.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByPlace(Place place);

    boolean existsByPlaceAndUser(Place place, User user);

    @Query("SELECT r FROM Review r WHERE r.place.id IN :placeIds AND r.user.userId = :userId")
    List<Review> findByPlaceIdsAndUserId(@Param("placeIds") List<UUID> placeIds, @Param("userId") Long userId);
}
