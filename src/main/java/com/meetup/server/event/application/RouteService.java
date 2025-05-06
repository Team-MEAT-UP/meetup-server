package com.meetup.server.event.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.dto.response.MeetingPoint;
import com.meetup.server.event.dto.response.RouteResponse;
import com.meetup.server.event.dto.response.RouteResponseList;
import com.meetup.server.event.persistence.EventRepository;
import com.meetup.server.global.clients.kakao.mobility.KakaoMobilityResponse;
import com.meetup.server.global.clients.odsay.OdsayTransitRouteSearchResponse;
import com.meetup.server.startpoint.application.RouteFacadeService;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.exception.StartPointErrorType;
import com.meetup.server.startpoint.exception.StartPointException;
import com.meetup.server.startpoint.persistence.StartPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteFacadeService routeFacadeService;
    private final StartPointRepository startPointRepository;
    private final EventRepository eventRepository;

    @Transactional
    public RouteResponseList getAllRouteDetails(
            UUID eventId,
            UUID startPointId
    ) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new StartPointException(StartPointErrorType.PLACE_NOT_FOUND));

        List<StartPoint> startPointList = startPointRepository.findAllByEvent(event);

        boolean isTransit = getIsTransit(startPointList, startPointId);
        double endX = getEndX(event);
        double endY = getEndY(event);


        List<RouteResponse> routeList = startPointList.stream()
                .map(startPoint -> fetchPerRouteDetails(
                        startPoint,
                        getStartX(startPoint),
                        getStartY(startPoint),
                        String.valueOf(endX),
                        String.valueOf(endY),
                        isTransit
                ))
                .toList();

        return RouteResponseList.of(routeList, MeetingPoint.of(getStartPointName(startPointList, startPointId), endX, endY));

    }

    private RouteResponse fetchPerRouteDetails(
            StartPoint startPoint,
            String startX, String startY, String endX, String endY,
            boolean isTransit
    ) {
        OdsayTransitRouteSearchResponse transitRoute = routeFacadeService.getTransitRoute(startX, startY, endX, endY);
        KakaoMobilityResponse drivingRoute = routeFacadeService.getDrivingRoute(startX, startY, endX, endY);

        if (transitRoute == null) {
            throw new StartPointException(StartPointErrorType.ODSAY_ERROR);
        }
        if (drivingRoute == null) {
            throw new StartPointException(StartPointErrorType.KAKAO_ERROR);
        }

        return RouteResponse.of(startPoint, startPoint.getUser(), transitRoute, drivingRoute, isTransit);
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

    private boolean getIsTransit(List<StartPoint> startPointList, UUID startPointId) {
        for (StartPoint startPoint : startPointList) {
            if (startPoint.getStartPointId().equals(startPointId)) {
                return startPoint.getIsTransit();
            }
        }
        return true;    //default
    }
}
