package com.meetup.server.recommend.persistence.init;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.persistence.EventRepository;
import com.meetup.server.global.support.DummyDataInit;
import com.meetup.server.recommend.domain.RecommendPlace;
import com.meetup.server.recommend.domain.type.RecommendCategory;
import com.meetup.server.recommend.persistence.RecommendPlaceRepository;
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

    private final RecommendPlaceRepository recommendPlaceRepository;
    private final EventRepository eventRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (recommendPlaceRepository.count() > 0) {
            log.info("[REVIEW]더미 데이터 존재");
        } else {
            List<RecommendPlace> placeList = new ArrayList<>();

            UUID eventId = UUID.fromString("0196e4c7-828b-7bad-a27b-e3fd6705004a");
            Event event = eventRepository.findByEventId(eventId);


            placeList.add(RecommendPlace.builder()
                    .placeName("스타벅스 강남점")
                    .location(Location.of(37.4979, 127.0276))
                    .category(RecommendCategory.CAFE)
                    .event(event)
                    .build());

            recommendPlaceRepository.saveAll(placeList);
        }
    }
}
