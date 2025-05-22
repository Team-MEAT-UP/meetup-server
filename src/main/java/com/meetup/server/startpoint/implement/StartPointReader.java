package com.meetup.server.startpoint.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.exception.StartPointErrorType;
import com.meetup.server.startpoint.exception.StartPointException;
import com.meetup.server.startpoint.persistence.StartPointRepository;
import com.meetup.server.user.dto.response.UserEventHistoryProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StartPointReader {

    private final StartPointRepository startPointRepository;

    public List<StartPoint> readAll(Event event) {
        return startPointRepository.findAllByEvent(event);
    }

    public StartPoint readById(UUID startPointId) {
        return startPointRepository.findById(startPointId)
                .orElseThrow(() -> new StartPointException(StartPointErrorType.PLACE_NOT_FOUND));
    }

    public String readName(UUID eventId) {
        return startPointRepository.findTopByEventIdOrderByCreatedAtAsc(eventId).getNonUserName();
    }

    public List<StartPoint> readAll(Long userId) {
        return startPointRepository.findAllByUserId(userId);
    }

    public List<StartPoint> readAll(List<StartPoint> userStartPoints) {
        return  userStartPoints.stream()
                .map(startPoint -> readAll(startPoint.getEvent()))
                .flatMap(List::stream)
                .toList();
    }

    public List<UserEventHistoryProjection> findUserEventsWithPaging(Long userId, UUID lastViewedEventId, int size) {
        return startPointRepository.findUserEventProjections(userId, lastViewedEventId, size);
    }

    public List<StartPoint> findParticipantsByEventIds(List<UUID> eventIds) {
        return startPointRepository.findParticipantsByEventIds(eventIds);
    }
}
