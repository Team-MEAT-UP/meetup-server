package com.meetup.server.startpoint.application;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.dto.response.EventStartPointResponse;
import com.meetup.server.event.implement.EventReader;
import com.meetup.server.global.clients.kakao.local.KakaoLocalResponse;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.dto.request.StartPointRequest;
import com.meetup.server.startpoint.implement.StartPointProcessor;
import com.meetup.server.startpoint.implement.StartPointReader;
import com.meetup.server.startpoint.implement.StartPointSearcher;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StartPointService {

    private final StartPointReader startPointReader;
    private final StartPointProcessor startPointProcessor;
    private final StartPointSearcher startPointSearcher;
    private final EventReader eventReader;
    private final UserReader userReader;

    public KakaoLocalResponse searchStartPoint(String textQuery) {
        return startPointSearcher.search(textQuery);
    }

    @Transactional
    @CacheEvict(value = "routeDetails", key = "#eventId")
    public EventStartPointResponse createStartPoint(UUID eventId, Long userId, StartPointRequest startPointRequest) {
        Event event = eventReader.read(eventId);

        Optional<User> optionalUser = userReader.readUserIfExists(userId);
        List<StartPoint> startPointList = startPointReader.readAllByEvent(event);

        if (userId != null && validateAlreadyHasStartPoint(userId, startPointList)) {
            StartPoint startPoint = startPointProcessor.save(
                    event,
                    null,
                    startPointRequest
            );
            return EventStartPointResponse.of(event, startPoint, startPointRequest.username());
        }

        StartPoint startPoint = startPointProcessor.save(
                event,
                optionalUser.orElse(null),
                startPointRequest
        );
        return EventStartPointResponse.of(event, startPoint, startPointRequest.username());
    }

    private boolean validateAlreadyHasStartPoint(Long userId, List<StartPoint> startPointList) {
        return startPointList
                .stream()
                .filter(StartPoint::getIsUser)
                .anyMatch(startPoint -> startPoint.getUser().getUserId().equals(userId));
    }
}
