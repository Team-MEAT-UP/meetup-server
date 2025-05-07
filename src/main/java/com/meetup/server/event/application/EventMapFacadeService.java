package com.meetup.server.event.application;

import com.meetup.server.event.dto.response.RouteResponseList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventMapFacadeService {

    private final MiddlePointService middlePointService;
    private final RouteService routeService;

    public RouteResponseList getEventMap(UUID eventId, UUID startPointId) {
        middlePointService.getMiddlePoint(eventId);
        return routeService.getAllRouteDetails(eventId, startPointId);
    }
}
