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
    private final RouteCacheService routeCacheService;

    public RouteResponseList getAllRouteDetails(
            MiddlePointResultResponse middlePointResultResponse,
            UUID startPointId,
            boolean isTransit
    ) {
        List<StartPoint> startPointList = middlePointResultResponse.startPoints();
        Map<UUID, Boolean> isTransitMap = buildIsTransitMap(startPointList, startPointId, isTransit);

        String endStationName = getEndStationName(middlePointResultResponse.event());
        double endX = getEndX(middlePointResultResponse.event());
        double endY = getEndY(middlePointResultResponse.event());

        String eventCacheKey = routeCacheService.generateEventCacheKey(middlePointResultResponse.event().getEventId());
        log.info("Event Cache Key : {}", eventCacheKey);

        RouteResponseList cachedRouteResponseList = routeCacheService.getCacheData(eventCacheKey, RouteResponseList.class);

        if (cachedRouteResponseList != null) {

            List<RouteResponse> newRoutes = startPointList.stream()
                    .map(startPoint -> fetchPerRouteDetails(
                            startPoint,
                            getStartX(startPoint),
                            getStartY(startPoint),
                            String.valueOf(endX),
                            String.valueOf(endY),
                            startPoint.getStartPointId(),
                            isTransitMap.getOrDefault(startPoint.getStartPointId(), true)
                    ))
                    .toList();

            if (!isCacheInvalid(cachedRouteResponseList.routeResponse(), newRoutes)) {
                log.info("Event Cache HIT");
                return cachedRouteResponseList;
            }

            log.info("Event Cache HIT AND Cache IsTransit MISMATCHED");
        }

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

        return RouteResponseList.of(routeList, MeetingPoint.of(endStationName, endX, endY), closestParkingLot);
    }

    private RouteResponse fetchPerRouteDetails(
            StartPoint startPoint,
            String startX, String startY, String endX, String endY,
            UUID startPointId,
            boolean newIsTransit
    ) {
        try {
            String cacheKey = generateCacheKeyForStartPoint(startPointId);
            RouteResponse cachedRouteResponse = routeCacheService.getCacheData(
                    cacheKey, RouteResponse.class
            );
            log.info("StartPoint Cache Key: {}", cacheKey);

            if (cachedRouteResponse != null) {
                if (cachedRouteResponse.isTransit() == newIsTransit) {
                    log.info("StartPoint Cache HIT : {}", startPoint.getStartPointId());
                    return cachedRouteResponse;
                } else {
                    log.info("StartPoint Cache HIT AND Cache IsTransit MISMATCHED : {}", startPoint.getStartPointId());
                    return routeCacheService.updateCacheOfIsTransit(startPoint.getStartPointId().toString(), newIsTransit);
                }
            }
        } catch (Exception e) {
            log.warn("StartPoint Cache Failed: {}", e.getMessage());
        }
        log.info("StartPoint Cache MISS");

        OdsayTransitRouteSearchResponse transitRoute = routeFacadeService.getTransitRoute(startX, startY, endX, endY);
        KakaoMobilityResponse drivingRoute = routeFacadeService.getDrivingRoute(startX, startY, endX, endY);

        if (transitRoute == null) {
            throw new StartPointException(StartPointErrorType.ODSAY_ERROR);
        }
        if (drivingRoute == null) {
            throw new StartPointException(StartPointErrorType.KAKAO_ERROR);
        }

        RouteResponse routeResponse = RouteResponse.of(
                startPoint,
                startPoint.getUser(),
                transitRoute, drivingRoute,
                newIsTransit
        );

        routeCacheService.putCacheData(generateCacheKeyForStartPoint(startPoint.getStartPointId()), routeResponse);
        return routeResponse;
    }

    private String getEndStationName(Event event) {
        return event.getSubway().getName();
    }

    private String generateCacheKeyForStartPoint(UUID startPointId) {
        return startPointId.toString();
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

    private Map<UUID, Boolean> buildIsTransitMap(List<StartPoint> startPointList, UUID startPointId, boolean newIsTransit) {
        Map<UUID, Boolean> isTransitMap = new HashMap<>();

        for (StartPoint startPoint : startPointList) {
            UUID mapKey = startPoint.getStartPointId();
            boolean isTransitValue = startPoint.getStartPointId().equals(startPointId) ? newIsTransit : startPoint.isTransit();
            isTransitMap.put(mapKey, isTransitValue);
        }

        return isTransitMap;
    }

    private boolean isCacheInvalid(List<RouteResponse> cachedRouteResponse, List<RouteResponse> current) {
        if (cachedRouteResponse.size() != current.size()) return true;
        for (int i = 0; i < cachedRouteResponse.size(); i++) {
            if (!cachedRouteResponse.get(i).equals(current.get(i))) return true;
        }
        return false;
    }
}
