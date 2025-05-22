package com.meetup.server.user.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.review.implement.ReviewChecker;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.implement.ParticipantExtractor;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.dto.response.UserEventHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserEventHistoryAssembler {

    private final ReviewChecker reviewChecker;
    private final ParticipantExtractor participantExtractor;

    public List<UserEventHistoryResponse> assemble(List<StartPoint> startPointList, List<StartPoint> allStartPoints, Long userId) {
        Map<Event, List<StartPoint>> eventStartPointsMap = startPointList.stream()
                .collect(Collectors.groupingBy(StartPoint::getEvent));

        return eventStartPointsMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getKey().getCreatedAt().compareTo(e1.getKey().getCreatedAt()))
                .filter(entry -> {
                            Event event = entry.getKey();
                            return participantExtractor.count(allStartPoints, event.getEventId()) > 1;
                        }
                )
                .map(entry -> {
                    Event event = entry.getKey();
                    List<User> loginParticipants = participantExtractor.extract(allStartPoints, event.getEventId());
                    int participantCount = participantExtractor.count(allStartPoints, event.getEventId());

                    boolean isReviewed = reviewChecker.isReviewed(event, userId);

                    return UserEventHistoryResponse.of(loginParticipants, event, participantCount, isReviewed);
                })
                .toList();
    }
}
