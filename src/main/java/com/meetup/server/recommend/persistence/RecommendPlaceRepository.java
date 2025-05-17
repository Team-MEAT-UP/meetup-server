package com.meetup.server.recommend.persistence;

import com.meetup.server.recommend.domain.RecommendPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendPlaceRepository extends JpaRepository<RecommendPlace, Long> {
}
