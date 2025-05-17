package com.meetup.server.startpoint.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.exception.StartPointErrorType;
import com.meetup.server.startpoint.exception.StartPointException;
import com.meetup.server.startpoint.persistence.StartPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StartPointReader {

    private final StartPointRepository startPointRepository;

    public List<StartPoint> readAllByEvent(Event event) {
        return startPointRepository.findAllByEvent(event);
    }

    public StartPoint readById(UUID startPointId) {
        return startPointRepository.findById(startPointId)
                .orElseThrow(() -> new StartPointException(StartPointErrorType.PLACE_NOT_FOUND));
    }

    public String readName(UUID eventId) {
        return startPointRepository.findTopByEventIdOrderByCreatedAtAsc(eventId).getNonUserName();
    }
}
