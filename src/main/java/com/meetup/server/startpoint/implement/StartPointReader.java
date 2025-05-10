package com.meetup.server.startpoint.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.persistence.StartPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StartPointReader {

    private final StartPointRepository startPointRepository;

    public List<StartPoint> readAllByEvent(Event event) {
        return startPointRepository.findAllByEvent(event);
    }
}
