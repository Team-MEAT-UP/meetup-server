package com.meetup.server.review.persistence;

import com.meetup.server.event.domain.Event;
import com.meetup.server.place.domain.Place;
import com.meetup.server.review.domain.Review;
import com.meetup.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {

    List<Review> findAllByPlace(Place place);

    boolean existsByEventAndPlaceAndUser(Event event, Place place, User user);
}
