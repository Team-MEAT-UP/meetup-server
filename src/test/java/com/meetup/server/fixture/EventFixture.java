package com.meetup.server.fixture;

import com.meetup.server.event.domain.Event;

public class EventFixture {

    public static Event getEvent() {
        return Event.builder()
                .build();
    }

}
