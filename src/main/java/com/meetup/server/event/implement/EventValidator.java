package com.meetup.server.event.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.exception.EventErrorType;
import com.meetup.server.event.exception.EventException;
import com.meetup.server.startpoint.persistence.StartPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventValidator {

    private static final int MAX_START_POINTS = 8;

    private final StartPointRepository startPointRepository;

    public void validateEventIsNotFull(Event event) {
        if (startPointRepository.countByEvent(event) >= MAX_START_POINTS) {
            throw new EventException(EventErrorType.START_POINT_LIMIT_EXCEEDED);
        }
    }
}
