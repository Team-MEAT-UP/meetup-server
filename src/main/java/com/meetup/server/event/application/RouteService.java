package com.meetup.server.event.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.dto.response.MeetingPoint;
import com.meetup.server.event.dto.response.RouteResponse;
import com.meetup.server.event.dto.response.RouteResponseList;
import com.meetup.server.event.implement.EventLocationInfoFinder;
import com.meetup.server.parkinglot.dto.ClosestParkingLot;
import com.meetup.server.parkinglot.implement.ParkingLotFinder;
import com.meetup.server.startpoint.domain.StartPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

    private final ParkingLotFinder parkingLotFinder;
    private final RouteDetailService routeDetailService;
    private final EventLocationInfoFinder eventLocationInfoFinder;

    public RouteResponseList getAllRouteDetails(
            Event event,
            List<StartPoint> startPointList,
            UUID startPointId
    ) {

        String endStationName = eventLocationInfoFinder.findEndStationName(event);
        double endX = eventLocationInfoFinder.findEndX(event);
        double endY = eventLocationInfoFinder.findEndY(event);

        List<RouteResponse> routeList = startPointList.stream()
                .map(startPoint -> routeDetailService.fetchPerRouteDetails(
                        startPoint,
                        eventLocationInfoFinder.findStartX(startPoint),
                        eventLocationInfoFinder.findStartY(startPoint),
                        String.valueOf(endX),
                        String.valueOf(endY),
                        startPointId
                ))
                .collect(Collectors.toList());

        prioritizeMyRoute(startPointId, routeList);

        ClosestParkingLot closestParkingLot = parkingLotFinder.findClosestParkingLot(event.getSubway().getPoint());

        return RouteResponseList.of(routeList, MeetingPoint.of(endStationName, endX, endY), closestParkingLot);
    }

    private void prioritizeMyRoute(UUID startPointId, List<RouteResponse> routeList) {
        if (startPointId != null) {
            Optional<RouteResponse> myRoute = routeList.stream()
                    .filter(RouteResponse::isMe)
                    .findFirst();

            myRoute.ifPresent(route -> {
                routeList.remove(route);
                routeList.addFirst(route);
            });
        }
    }
}
