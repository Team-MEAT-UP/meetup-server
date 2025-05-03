package com.meetup.server.event.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.persistence.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventProcessor {

    private final EventRepository eventRepository;

    public Event save() {
        Event event = Event.builder().build();
        return eventRepository.save(event);
    }
}
