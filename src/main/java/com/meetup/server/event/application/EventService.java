package com.meetup.server.event.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.dto.response.EventStartPointResponse;
import com.meetup.server.event.implement.EventProcessor;
import com.meetup.server.event.implement.EventReader;
import com.meetup.server.global.util.CoordinateUtil;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.dto.request.StartPointRequest;
import com.meetup.server.startpoint.implement.StartPointProcessor;
import com.meetup.server.startpoint.implement.StartPointReader;
import com.meetup.server.subway.domain.Subway;
import com.meetup.server.subway.exception.SubwayErrorType;
import com.meetup.server.subway.exception.SubwayException;
import com.meetup.server.subway.implement.processor.SubwayPathResult;
import com.meetup.server.subway.implement.processor.SubwayProcessor;
import com.meetup.server.subway.implement.reader.SubwayReader;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final UserReader userReader;
    private final EventReader eventReader;
    private final EventProcessor eventProcessor;
    private final StartPointReader startPointReader;
    private final StartPointProcessor startPointProcessor;
    private final SubwayReader subwayReader;
    private final SubwayProcessor subwayProcessor;

    @Transactional
    public EventStartPointResponse createEvent(Long userId, StartPointRequest startPointRequest) {
        Event event = eventProcessor.save();

        Optional<User> optionalUser = userReader.readUserIfExists(userId);
        StartPoint startPoint = startPointProcessor.save(
                event,
                optionalUser.orElse(null),
                startPointRequest
        );
        return EventStartPointResponse.of(event, startPoint, startPointRequest.username());
    }

    @Transactional
    public void getEventMap(UUID eventId) {
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
