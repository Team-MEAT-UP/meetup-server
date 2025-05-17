package com.meetup.server.event.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.dto.response.MeetingPoint;
import com.meetup.server.event.dto.response.RouteResponse;
import com.meetup.server.event.dto.response.RouteResponseList;
import com.meetup.server.event.implement.EventLocationInfoFinder;
import com.meetup.server.parkinglot.dto.ClosestParkingLot;
import com.meetup.server.parkinglot.implement.ParkingLotFinder;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.implement.StartPointReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

    private final ParkingLotFinder parkingLotFinder;
    private final RouteDetailService routeDetailService;
    private final EventLocationInfoFinder eventLocationInfoFinder;
    private final StartPointReader startPointReader;

    public RouteResponseList getAllRouteDetails(Event event, List<StartPoint> startPointList) {

        String endStationName = eventLocationInfoFinder.findEndStationName(event);
        double endX = eventLocationInfoFinder.findEndX(event);
        double endY = eventLocationInfoFinder.findEndY(event);

        List<RouteResponse> routeList = startPointList.stream()
                .map(startPoint -> routeDetailService.fetchPerRouteDetails(
                        startPoint,
                        eventLocationInfoFinder.findStartX(startPoint),
                        eventLocationInfoFinder.findStartY(startPoint),
                        String.valueOf(endX),
                        String.valueOf(endY)
                ))
                .collect(Collectors.toList());

        ClosestParkingLot closestParkingLot = parkingLotFinder.findClosestParkingLot(event.getSubway().getPoint());

        String eventMaker = startPointReader.readName(event.getEventId());
        return RouteResponseList.of(eventMaker, routeList, MeetingPoint.of(endStationName, endX, endY), closestParkingLot);
    }
}
