package com.meetup.server.event.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.implement.EventProcessor;
import com.meetup.server.startpoint.dto.request.StartPointRequest;
import com.meetup.server.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final UserReader userReader;
    private final EventProcessor eventProcessor;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public UUID createEvent(Long userId, StartPointRequest startPointRequest) {
        Event event = userReader.readUserIfExists(userId)
                .map(eventProcessor::saveForLoggedInUser)
                .orElseGet(eventProcessor::saveForGuestUser);

        // todo. 출발지(startpoint) 생성 기능을 구현하여 처리하기
        applicationEventPublisher.publishEvent(startPointRequest);

        return event.getEventId();
    }
}
