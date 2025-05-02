package com.meetup.server.event.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.persistence.EventRepository;
import com.meetup.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventProcessor {

    private final EventRepository eventRepository;

    public Event saveForLoggedInUser(User user) {
        Event event = Event.builder()
                .user(user)
                .build();
        return eventRepository.save(event);
    }

    public Event saveForGuestUser() {
        Event event = Event.builder().build();
        return eventRepository.save(event);
    }
}
