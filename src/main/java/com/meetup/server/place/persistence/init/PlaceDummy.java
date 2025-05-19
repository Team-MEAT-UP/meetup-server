package com.meetup.server.place.persistence.init;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.persistence.EventRepository;
import com.meetup.server.global.support.DummyDataInit;
import com.meetup.server.place.domain.Place;
import com.meetup.server.place.domain.type.PlaceCategory;
import com.meetup.server.place.persistence.PlaceRepository;
import com.meetup.server.startpoint.domain.type.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Profile("local")
@Order(2)
@DummyDataInit
public class PlaceDummy implements ApplicationRunner {

    private final PlaceRepository placeRepository;
    private final EventRepository eventRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (placeRepository.count() > 0) {
            log.info("[REVIEW]더미 데이터 존재");
        } else {
            List<Place> placeList = new ArrayList<>();

            UUID eventId = UUID.fromString("0196e8b5-2701-71ef-85df-ff58ed3980c8");
            Event event = eventRepository.findByEventId(eventId);


            placeList.add(Place.builder()
                    .name("스타벅스 강남점")
                    .googlePlaceId("GOOGLE_PLACE_ID")
                    .kakaoPlaceId("KAKAO_PLACE_ID")
                    .location(Location.of(37.4979, 127.0276))
                    .category(PlaceCategory.CAFE)
                    .event(event)
                    .build());

            placeRepository.saveAll(placeList);
        }
    }
}
