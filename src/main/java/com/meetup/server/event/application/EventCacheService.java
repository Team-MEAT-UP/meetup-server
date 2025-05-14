package com.meetup.server.event.application;

import com.meetup.server.event.dto.response.MiddlePointResultResponse;
import com.meetup.server.event.dto.response.RouteResponseList;
import com.meetup.server.event.implement.EventProcessor;
import com.meetup.server.event.implement.EventReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventCacheService {

    private final MiddlePointService middlePointService;
    private final RouteService routeService;
    private final EventReader eventReader;
    private final EventProcessor eventProcessor;

    @Cacheable(value = "routeDetails", key = "#eventId", unless = "#result == null")
    public RouteResponseList getEventMap(UUID eventId) {
        MiddlePointResultResponse result = middlePointService.getMiddlePoint(eventId);
        return routeService.getAllRouteDetails(result.event(), result.startPoints());
    }

    @Transactional
    @CachePut(value = "routeDetails", key = "#eventId")
    public RouteResponseList updateTransit(UUID eventId, UUID startPointId, boolean isTransit) {
        RouteResponseList routeResponseList = eventReader.readEventCache(eventId);
        eventProcessor.updateTransitForStartPoint(routeResponseList, startPointId, isTransit);
        return routeResponseList;
    }
}
