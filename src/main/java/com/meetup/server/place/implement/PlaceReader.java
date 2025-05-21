package com.meetup.server.place.implement;

import com.meetup.server.place.domain.Place;
import com.meetup.server.place.exception.PlaceErrorType;
import com.meetup.server.place.exception.PlaceException;
import com.meetup.server.place.persistence.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PlaceReader {

    private final PlaceRepository placeRepository;

    public Place read(UUID placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceException(PlaceErrorType.PLACE_NOT_FOUND));
    }
}
