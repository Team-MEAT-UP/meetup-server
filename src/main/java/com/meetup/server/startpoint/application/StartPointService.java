package com.meetup.server.startpoint.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.dto.response.EventStartPointResponse;
import com.meetup.server.event.implement.EventReader;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.dto.request.StartPointRequest;
import com.meetup.server.startpoint.implement.StartPointProcessor;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StartPointService {

    private final StartPointProcessor startPointProcessor;
    private final EventReader eventReader;
    private final UserReader userReader;

    @Transactional
    public EventStartPointResponse createStartPoint(UUID eventId, Long userId, StartPointRequest startPointRequest) {
        Event event = eventReader.read(eventId);

        Optional<User> optionalUser = userReader.readUserIfExists(userId);
        StartPoint startPoint = startPointProcessor.save(
                event,
                optionalUser.orElse(null),
                startPointRequest
        );
        return EventStartPointResponse.of(event, startPoint, startPointRequest.username());
    }
}
