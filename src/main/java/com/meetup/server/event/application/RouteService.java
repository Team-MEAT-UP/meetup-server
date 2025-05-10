package com.meetup.server.event.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.dto.response.MeetingPoint;
import com.meetup.server.event.dto.response.MiddlePointResultResponse;
import com.meetup.server.event.dto.response.RouteResponse;
import com.meetup.server.event.dto.response.RouteResponseList;
import com.meetup.server.global.clients.kakao.mobility.KakaoMobilityResponse;
import com.meetup.server.global.clients.odsay.OdsayTransitRouteSearchResponse;
import com.meetup.server.parkinglot.dto.ClosestParkingLot;
import com.meetup.server.parkinglot.implement.ParkingLotFinder;
import com.meetup.server.startpoint.application.RouteFacadeService;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.exception.StartPointErrorType;
import com.meetup.server.startpoint.exception.StartPointException;
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

    private final RouteFacadeService routeFacadeService;
    private final ParkingLotFinder parkingLotFinder;

    public RouteResponseList getAllRouteDetails(
            MiddlePointResultResponse middlePointResultResponse,
            UUID startPointId
    ) {
        List<StartPoint> startPointList = middlePointResultResponse.startPoints();

        String endStationName = getEndStationName(middlePointResultResponse.event());
        double endX = getEndX(middlePointResultResponse.event());
        double endY = getEndY(middlePointResultResponse.event());

        List<RouteResponse> routeList = startPointList.stream()
                .map(startPoint -> fetchPerRouteDetails(
                        startPoint,
                        getStartX(startPoint),
                        getStartY(startPoint),
                        String.valueOf(endX),
                        String.valueOf(endY),
                        startPointId
                ))
                .collect(Collectors.toList());

        prioritizeMyRoute(startPointId, routeList);

        ClosestParkingLot closestParkingLot = parkingLotFinder.findClosestParkingLot(middlePointResultResponse.event().getSubway().getPoint());

        return RouteResponseList.of(routeList, MeetingPoint.of(endStationName, endX, endY), closestParkingLot);
    }

    private RouteResponse fetchPerRouteDetails(
            StartPoint startPoint,
            String startX, String startY, String endX, String endY,
            UUID startPointId
    ) {
        OdsayTransitRouteSearchResponse transitRoute = routeFacadeService.getTransitRoute(startX, startY, endX, endY);
        KakaoMobilityResponse drivingRoute = routeFacadeService.getDrivingRoute(startX, startY, endX, endY);

        if (transitRoute == null) {
            throw new StartPointException(StartPointErrorType.ODSAY_ERROR);
        }
        if (drivingRoute == null) {
            throw new StartPointException(StartPointErrorType.KAKAO_ERROR);
        }

        return RouteResponse.of(startPoint, startPoint.getUser(), transitRoute, drivingRoute, getIsMe(startPoint, startPointId));
    }

    private String getEndStationName(Event event) {
        return event.getSubway().getName();
    }

    private double getEndX(Event event) {
        return event.getSubway().getLocation().getRoadLongitude();
    }

    private double getEndY(Event event) {
        return event.getSubway().getLocation().getRoadLatitude();
    }

    private String getStartX(StartPoint startPoint) {
        return String.valueOf(startPoint.getLocation().getRoadLongitude());
    }

    private String getStartY(StartPoint startPoint) {
        return String.valueOf(startPoint.getLocation().getRoadLatitude());
    }

    private boolean getIsMe(StartPoint startPoint, UUID startPointId) {
        return startPoint.getStartPointId().equals(startPointId);
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
