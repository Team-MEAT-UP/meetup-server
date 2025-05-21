package com.meetup.server.place.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.implement.EventReader;
import com.meetup.server.place.domain.Place;
import com.meetup.server.place.dto.response.PlaceResponse;
import com.meetup.server.place.dto.response.PlaceResponseList;
import com.meetup.server.place.implement.PlaceProcessor;
import com.meetup.server.place.implement.PlaceReader;
import com.meetup.server.place.persistence.projection.PlaceWithDistance;
import com.meetup.server.review.implement.ReviewReader;
import com.meetup.server.review.persistence.projection.PlaceWithRating;
import com.meetup.server.subway.domain.Subway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private static final double RADIUS = 500;

    private final PlaceReader placeReader;
    private final PlaceProcessor placeProcessor;
    private final EventReader eventReader;
    private final ReviewReader reviewReader;

    @Transactional
    public void confirmPlace(UUID eventId, UUID placeId) {
        Event event = eventReader.read(eventId);
        Place place = placeReader.read(placeId);

        if (event.hasNoPlace()) {
            event.confirmPlace(place);
        } else {
            event.updatePlace(place);
        }
    }

    @Transactional(readOnly = true)
    public PlaceResponseList findAllPlaces(UUID eventId) {
        Event event = eventReader.read(eventId);
        Subway subway = event.getSubway();

        Place confirmedPlace = event.getPlace();
        PlaceWithDistance confirmedPlaceWithDistance = placeReader.readWithDistance(confirmedPlace, subway.getPoint());
        PlaceWithRating confirmedPlaceWithRating = reviewReader.readPlaceRatingsAsMap(List.of(confirmedPlace.getId())).get(confirmedPlace);

        List<PlaceWithDistance> nearbyPlaces = placeReader.readAllWithinRadius(subway.getPoint(), RADIUS);
        List<PlaceResponse> recommendedPlaces = placeProcessor.getRecommendedPlaces(confirmedPlace, nearbyPlaces);

        return PlaceResponseList.of(subway, PlaceResponse.of(confirmedPlaceWithDistance, confirmedPlaceWithRating), recommendedPlaces);
    }
}
