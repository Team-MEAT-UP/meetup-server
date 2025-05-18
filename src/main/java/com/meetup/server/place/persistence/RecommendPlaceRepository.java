package com.meetup.server.place.persistence;

import com.meetup.server.place.domain.RecommendPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecommendPlaceRepository extends JpaRepository<RecommendPlace, UUID> {

    boolean existsByKakaoPlaceId(String kakaoPlaceId);
}
