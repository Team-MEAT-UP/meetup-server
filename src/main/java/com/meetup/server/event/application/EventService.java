package com.meetup.server.event.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.dto.response.EventStartPointResponse;
import com.meetup.server.event.dto.response.RouteResponseList;
import com.meetup.server.event.implement.EventProcessor;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.dto.request.StartPointRequest;
import com.meetup.server.startpoint.implement.StartPointProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventProcessor eventProcessor;
    private final StartPointProcessor startPointProcessor;
    private final EventCacheService eventCacheService;

    @Transactional
    public EventStartPointResponse createEvent(Long userId, UUID guestId, StartPointRequest startPointRequest) {
        Event event = eventProcessor.save();
        StartPoint startPoint = startPointProcessor.save(event, userId, guestId, startPointRequest);
        return EventStartPointResponse.of(event, startPoint, startPointRequest.username());
    }

    @Transactional
    public RouteResponseList getEventMap(UUID eventId, Long userId, UUID guestId) {
        RouteResponseList eventMap = eventCacheService.getEventMap(eventId);
        eventProcessor.prioritizeMyRoute(userId, guestId, eventMap.getRouteResponse());
        return eventMap;
    }
}
