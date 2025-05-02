package com.meetup.server.event.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.persistence.EventRepository;
import com.meetup.server.fixture.UserFixture;
import com.meetup.server.startpoint.dto.request.StartPointRequest;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(UserFixture.getUser());
    }

    @Test
    void 비로그인_사용자가_이벤트를_생성한다() {
        StartPointRequest startPointRequest = new StartPointRequest(
                null, "선정릉역 수인분당선", "서울특별시 강남구 삼성동 111-114", "서울특별시 강남구 선릉로 지하580",
                BigDecimal.valueOf(127.043999), BigDecimal.valueOf(37.510297)
        );
        UUID eventId = eventService.createEvent(null, startPointRequest);

        assertThat(eventRepository.findById(eventId)).isPresent();
    }

    @Test
    void 로그인_사용자가_이벤트를_생성한다() {
        StartPointRequest startPointRequest = new StartPointRequest(
                "땡수팟", "선정릉역 수인분당선", "서울특별시 강남구 삼성동 111-114", "서울특별시 강남구 선릉로 지하580",
                BigDecimal.valueOf(127.043999), BigDecimal.valueOf(37.510297)
        );
        UUID eventId = eventService.createEvent(user.getUserId(), startPointRequest);

        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        assertThat(optionalEvent).isPresent();

        Event event = optionalEvent.get();
        assertThat(event.getUser().getUserId()).isEqualTo(user.getUserId());
    }

}
