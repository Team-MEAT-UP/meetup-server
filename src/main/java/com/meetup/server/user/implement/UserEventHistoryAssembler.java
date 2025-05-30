package com.meetup.server.user.implement;

import com.meetup.server.global.util.TimeUtil;
import com.meetup.server.review.implement.ReviewReader;
import com.meetup.server.startpoint.implement.StartPointReader;
import com.meetup.server.startpoint.persistence.projection.EventHistory;
import com.meetup.server.startpoint.persistence.projection.ParticipantCount;
import com.meetup.server.startpoint.persistence.projection.Participant;
import com.meetup.server.user.dto.response.UserEventHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserEventHistoryAssembler {

    private final ReviewReader reviewReader;
    private final StartPointReader startPointReader;

    public List<UserEventHistoryResponse> assemble(List<EventHistory> eventHistories, Long userId) {
        List<UUID> eventIds = eventHistories.stream()
                .map(EventHistory::eventId)
                .toList();

        Map<UUID, List<String>> imageUrlMap = getParticipantsWithImageUrls(eventIds);
        Map<UUID, Integer> participantsMap = getParticipantsCount(eventIds);
        Map<UUID, Boolean> isReviewedMap = reviewReader.readReviewsWrittenByUser(eventHistories, userId);

        return eventHistories.stream()
                .map(event -> {
                    List<String> imageUrls = imageUrlMap.getOrDefault(event.eventId(), List.of());

                    return UserEventHistoryResponse.builder()
                            .eventId(event.eventId())
                            .middlePointName(event.subwayName())
                            .placeName(event.placeName())
                            .participatedPeopleCount(participantsMap.getOrDefault(event.eventId(), 0))
                            .userProfileImageUrls(imageUrls)
                            .eventMadeAgo(TimeUtil.calculateDaysAgo(event.createdAt()))
                            .eventHourAgo(TimeUtil.calculateHoursAgo(event.createdAt()))
                            .isReviewed(isReviewedMap.getOrDefault(event.eventId(), false))
                            .build();
                }).toList();
    }

    private Map<UUID, List<String>> getParticipantsWithImageUrls(List<UUID> eventIds) {
        return startPointReader.readParticipantsWithUrls(eventIds).stream()
                .collect(Collectors.groupingBy(
                        Participant::eventId,
                        Collectors.mapping(
                                Participant::profileImageUrl,
                                Collectors.toList())
                ));
    }

    private Map<UUID, Integer> getParticipantsCount(List<UUID> eventIds) {
        return startPointReader.readParticipantCounts(eventIds).stream()
                .collect(Collectors.toMap(
                        ParticipantCount::eventId,
                        participants -> participants.count().intValue()
                ));
    }
}
