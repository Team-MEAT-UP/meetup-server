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
import java.util.UUID;

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
                .toList();

        ClosestParkingLot closestParkingLot = parkingLotFinder.findClosestParkingLot(middlePointResultResponse.event().getSubway().getPoint());

        return RouteResponseList.of(routeList, MeetingPoint.of(getStartPointName(startPointList, startPointId), endX, endY), closestParkingLot);
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

        return RouteResponse.of(startPoint, startPoint.getUser(), transitRoute, drivingRoute, getIsTransit(startPoint, startPointId));
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

    private String getStartPointName(List<StartPoint> startPointList, UUID startPointId) {
        for (StartPoint startPoint : startPointList) {
            if (startPoint.getStartPointId().equals(startPointId)) {
                return startPoint.getName();
            }
        }
        return null;
    }

    private boolean getIsTransit(StartPoint startPoint, UUID startPointId) {
        if (startPoint.getStartPointId().equals(startPointId)) {
            return startPoint.isTransit();
        }
        return true;    // default 값
    }
}
