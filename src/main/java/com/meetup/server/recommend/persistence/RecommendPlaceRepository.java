package com.meetup.server.recommend.persistence;

import com.meetup.server.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendPlaceRepository extends JpaRepository<Place, Long> {
}
