package com.meetup.server.event.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.persistence.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("h2")
@SpringBootTest
class EventProcessorTest {

    @Autowired
    private EventProcessor eventProcessor;

    @Autowired
    private EventRepository eventRepository;

    @Test
    void 사용자가_이벤트를_저장한다() {
        Event event = eventProcessor.save();
        assertThat(eventRepository.findById(event.getEventId())).isPresent();
    }

}
