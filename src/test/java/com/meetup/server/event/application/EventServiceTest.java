package com.meetup.server.event.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.dto.response.EventStartPointResponse;
import com.meetup.server.event.persistence.EventRepository;
import com.meetup.server.fixture.StartPointFixture;
import com.meetup.server.fixture.UserFixture;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.dto.request.StartPointRequest;
import com.meetup.server.startpoint.persistence.StartPointRepository;
import com.meetup.server.support.IntegrationTestContainer;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class EventServiceTest extends IntegrationTestContainer {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private StartPointRepository startPointRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private StartPointRequest startPointRequest;

    @BeforeEach
    void setUp() {
        user = userRepository.save(UserFixture.getUser());
        startPointRequest = StartPointFixture.getStartPointRequest();
    }

    @Test
    void 비로그인_사용자가_이벤트를_생성한다() {
        EventStartPointResponse eventStartPointResponse = eventService.createEvent(null, startPointRequest);

        Optional<Event> optionalEvent = eventRepository.findById(eventStartPointResponse.eventId());
        assertThat(optionalEvent).isPresent();

        Optional<StartPoint> optionalStartPoint = startPointRepository.findById(eventStartPointResponse.startPointId());
        assertThat(optionalStartPoint).isPresent();
        assertThat(eventStartPointResponse.username()).isEqualTo(startPointRequest.username());
        assertThat(optionalStartPoint.get().getStartPointId()).isEqualTo(eventStartPointResponse.startPointId());
        assertThat(optionalStartPoint.get().getName()).isEqualTo(startPointRequest.startPoint());
        assertThat(optionalStartPoint.get().getUser()).isNull();
        assertThat(optionalStartPoint.get().getAddress().getAddress()).isEqualTo(startPointRequest.address());
        assertThat(optionalStartPoint.get().getAddress().getRoadAddress()).isEqualTo(startPointRequest.roadAddress());
        assertThat(optionalStartPoint.get().getLocation().getRoadLongitude()).isEqualTo(startPointRequest.longitude());
        assertThat(optionalStartPoint.get().getLocation().getRoadLatitude()).isEqualTo(startPointRequest.latitude());
    }

    @Transactional
    @Test
    void 로그인_사용자가_이벤트를_생성한다() {
        EventStartPointResponse eventStartPointResponse = eventService.createEvent(user.getUserId(), startPointRequest);

        Optional<Event> optionalEvent = eventRepository.findById(eventStartPointResponse.eventId());
        assertThat(optionalEvent).isPresent();

        Optional<StartPoint> optionalStartPoint = startPointRepository.findById(eventStartPointResponse.startPointId());
        assertThat(optionalStartPoint).isPresent();
        assertThat(eventStartPointResponse.username()).isEqualTo(startPointRequest.username());
        assertThat(optionalStartPoint.get().getStartPointId()).isEqualTo(eventStartPointResponse.startPointId());
        assertThat(optionalStartPoint.get().getName()).isEqualTo(startPointRequest.startPoint());
        assertThat(optionalStartPoint.get().getUser()).isEqualTo(user);
        assertThat(optionalStartPoint.get().getAddress().getAddress()).isEqualTo(startPointRequest.address());
        assertThat(optionalStartPoint.get().getAddress().getRoadAddress()).isEqualTo(startPointRequest.roadAddress());
        assertThat(optionalStartPoint.get().getLocation().getRoadLongitude()).isEqualTo(startPointRequest.longitude());
        assertThat(optionalStartPoint.get().getLocation().getRoadLatitude()).isEqualTo(startPointRequest.latitude());
    }

}
