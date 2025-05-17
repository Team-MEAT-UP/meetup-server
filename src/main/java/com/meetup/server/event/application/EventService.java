package com.meetup.server.event.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.dto.response.EventStartPointResponse;
import com.meetup.server.event.dto.response.RouteResponseList;
import com.meetup.server.event.implement.EventProcessor;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.dto.request.StartPointRequest;
import com.meetup.server.startpoint.implement.StartPointProcessor;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final UserReader userReader;
    private final EventProcessor eventProcessor;
    private final StartPointProcessor startPointProcessor;
    private final EventCacheService eventCacheService;

    @Transactional
    public EventStartPointResponse createEvent(Long userId, StartPointRequest startPointRequest) {
        Event event = eventProcessor.save();

        Optional<User> optionalUser = userReader.readUserIfExists(userId);
        StartPoint startPoint = startPointProcessor.save(
                event,
                optionalUser.orElse(null),
                startPointRequest
        );
        return EventStartPointResponse.of(event, startPoint, startPointRequest.username());
    }

    @Transactional
    public RouteResponseList getEventMap(UUID eventId, UUID startPointId) {
        RouteResponseList eventMap = eventCacheService.getEventMap(eventId);
        eventProcessor.prioritizeMyRoute(startPointId, eventMap.getRouteResponse());
        return eventMap;
    }
}
