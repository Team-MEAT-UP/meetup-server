package com.meetup.server.startpoint.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.dto.response.EventStartPointResponse;
import com.meetup.server.event.persistence.EventRepository;
import com.meetup.server.fixture.EventFixture;
import com.meetup.server.fixture.StartPointFixture;
import com.meetup.server.fixture.UserFixture;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.dto.request.StartPointRequest;
import com.meetup.server.startpoint.persistence.StartPointRepository;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StartPointServiceTest {

    @Autowired
    private StartPointService startPointService;

    @Autowired
    private StartPointRepository startPointRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    private Event event;
    private StartPointRequest startPointRequest;
    private User user;

    @BeforeEach
    void setUp() {
        event = eventRepository.save(EventFixture.getEvent());
        startPointRequest = StartPointFixture.getStartPointRequest();
        user = userRepository.save(UserFixture.getUser());
    }

    @Test
    void 비로그인_사용자가_출발지를_저장한다() {
        EventStartPointResponse eventStartPointResponse = startPointService.createStartPoint(
                event.getEventId(),
                null,
                startPointRequest
        );

        assertThat(event.getEventId()).isEqualTo(eventStartPointResponse.eventId());

        Optional<StartPoint> optionalStartPoint = startPointRepository.findById(eventStartPointResponse.startPointId());
        assertThat(optionalStartPoint).isPresent();
        assertThat(optionalStartPoint.get().getStartPointId()).isEqualTo(eventStartPointResponse.startPointId());
        assertThat(optionalStartPoint.get().getUser()).isEqualTo(null);
    }

    @Transactional
    @Test
    void 로그인_사용자가_출발지를_저장한다() {
        EventStartPointResponse eventStartPointResponse = startPointService.createStartPoint(
                event.getEventId(),
                user.getUserId(),
                startPointRequest
        );

        assertThat(event.getEventId()).isEqualTo(eventStartPointResponse.eventId());

        Optional<StartPoint> optionalStartPoint = startPointRepository.findById(eventStartPointResponse.startPointId());
        assertThat(optionalStartPoint).isPresent();
        assertThat(optionalStartPoint.get().getStartPointId()).isEqualTo(eventStartPointResponse.startPointId());
        assertThat(optionalStartPoint.get().getUser()).isEqualTo(user);
    }
}
