package com.meetup.server.place.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.implement.EventReader;
import com.meetup.server.place.domain.Place;
import com.meetup.server.place.implement.PlaceReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final EventReader eventReader;
    private final PlaceReader placeReader;

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
}
