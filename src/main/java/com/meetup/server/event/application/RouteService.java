package com.meetup.server.event.application;

import com.meetup.server.event.domain.Event;
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
        String endX = getEndX(event);
        String endY = getEndY(event);

        List<RouteResponse> routeList = startPointList.stream()
                .map(startPoint -> fetchPerRouteDetails(
                        startPoint,
                        getStartX(startPoint),
                        getStartY(startPoint),
                        endX,
                        endY,
                        isTransit
                ))
                .toList();

        return RouteResponseList.of(routeList, isTransit);
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

    private String getEndX(Event event) {
        return event.getSubway().getLocation().getRoadLongitude().toString();
    }

    private String getEndY(Event event) {
        return event.getSubway().getLocation().getRoadLatitude().toString();
    }

    private String getStartX(StartPoint startPoint) {
        return startPoint.getLocation().getRoadLongitude().toString();
    }

    private String getStartY(StartPoint startPoint) {
        return startPoint.getLocation().getRoadLatitude().toString();
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
