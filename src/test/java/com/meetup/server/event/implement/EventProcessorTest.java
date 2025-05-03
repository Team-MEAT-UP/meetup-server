package com.meetup.server.event.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.persistence.EventRepository;
import com.meetup.server.fixture.UserFixture;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EventProcessorTest {

    @Autowired
    private EventProcessor eventProcessor;

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
    void 비로그인_사용자가_이벤트를_저장한다() {
        Event event = eventProcessor.saveForGuestUser();
        assertThat(eventRepository.findById(event.getEventId())).isPresent();
    }

    @Test
    void 로그인_사용자가_이벤트를_저장한다() {
        Event event = eventProcessor.saveForLoggedInUser(user);
        assertThat(event.getUser().getUserId()).isEqualTo(user.getUserId());
    }
}
