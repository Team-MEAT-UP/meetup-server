package com.meetup.server.place.persistence;

import com.meetup.server.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID> {

    boolean existsByKakaoPlaceId(String kakaoPlaceId);
}
