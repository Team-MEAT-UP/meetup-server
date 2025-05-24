package com.meetup.server.startpoint.implement;

import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ParticipantExtractor {

    public List<User> extract(List<StartPoint> allStartPoints, UUID eventId) {
        return allStartPoints.stream()
                .filter(StartPoint::getIsUser)
                .filter(startPoint -> startPoint.getEvent().getEventId().equals(eventId))
                .map(StartPoint::getUser)
                .toList();
    }

    public int count(List<StartPoint> allStartPoints, UUID eventId) {
        return allStartPoints.stream()
                .filter(startPoint -> startPoint.getEvent().getEventId().equals(eventId))
                .toList().size();
    }
}
