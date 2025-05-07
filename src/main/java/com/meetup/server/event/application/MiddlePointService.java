package com.meetup.server.event.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.implement.EventReader;
import com.meetup.server.global.util.CoordinateUtil;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.implement.StartPointReader;
import com.meetup.server.subway.domain.Subway;
import com.meetup.server.subway.exception.SubwayErrorType;
import com.meetup.server.subway.exception.SubwayException;
import com.meetup.server.subway.implement.processor.SubwayPathResult;
import com.meetup.server.subway.implement.processor.SubwayProcessor;
import com.meetup.server.subway.implement.reader.SubwayReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MiddlePointService {

    private final EventReader eventReader;
    private final StartPointReader startPointReader;
    private final SubwayProcessor subwayProcessor;
    private final SubwayReader subwayReader;

    public void getMiddlePoint(UUID eventId) {
        Event event = eventReader.read(eventId);
        List<StartPoint> startPoints = startPointReader.readAllByEvent(event);

        Map<StartPoint, Subway> startPointToSubwayMap = subwayProcessor.mapStartPointsToClosestSubway(startPoints);

        Point centerPoint = CoordinateUtil.calculateCenterPoint(startPoints.stream().map(StartPoint::getPoint).toList());
        List<Subway> nearbySubways = subwayReader.readNearbySubways(centerPoint);

        Map<StartPoint, List<SubwayPathResult>> startPointToSubwayPathsMap = subwayProcessor.mapStartPointsToDestinationSubways(startPoints, startPointToSubwayMap, nearbySubways);

        subwayProcessor.findMostFairSubway(startPointToSubwayPathsMap).ifPresentOrElse(subwayId -> {
            Subway subway = subwayReader.read(subwayId);
            log.info("중간지점 Subway: {} ({})", subway.getName(), subway.getSubwayId());
        }, () -> {
            throw new SubwayException(SubwayErrorType.SUBWAY_NOT_FOUND);
        });
    }
}
