package com.meetup.server.user.implement;

import com.meetup.server.global.util.TimeUtil;
import com.meetup.server.review.implement.ReviewChecker;
import com.meetup.server.startpoint.implement.StartPointReader;
import com.meetup.server.startpoint.persistence.projection.EventHistoryProjection;
import com.meetup.server.startpoint.persistence.projection.ParticipantCountProjection;
import com.meetup.server.startpoint.persistence.projection.ParticipantProjection;
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

    private final ReviewChecker reviewChecker;
    private final StartPointReader startPointReader;

    public List<UserEventHistoryResponse> assemble(List<EventHistoryProjection> displayEvents, Long userId) {
        List<UUID> eventIds = displayEvents.stream()
                .map(EventHistoryProjection::eventId)
                .toList();

        Map<UUID, List<String>> imageUrlMap = getParticipantsWithImageUrls(eventIds);
        Map<UUID, Integer> participantsMap = getParticipantsCount(eventIds);
        Map<UUID, Boolean> isReviewedMap = reviewChecker.isReviewed(displayEvents, userId);

        return displayEvents.stream()
                .filter(event -> {
                    return participantsMap.getOrDefault(event.eventId(), 0) > 1 ;
                })
                .map(event -> {
                    List<String> imageUrls = imageUrlMap.getOrDefault(event.eventId(), List.of());

                    return UserEventHistoryResponse.builder()
                            .eventId(event.eventId())
                            .middlePointName(event.subwayName())
                            .placeName(event.placeName())
                            .participatedPeopleCount(participantsMap.getOrDefault(event.eventId(), 0))
                            .userProfileImageUrls(imageUrls)
                            .eventMadeAgo(TimeUtil.calculateDaysAgo(event.createdAt()))
                            .eventTimeAgo(TimeUtil.calculateTimeAgo(event.createdAt()))
                            .isReviewed(isReviewedMap.getOrDefault(event.eventId(), false))
                            .build();
                }).toList();
    }

    private Map<UUID, List<String>> getParticipantsWithImageUrls(List<UUID> eventIds) {
        return startPointReader.findParticipants(eventIds).stream()
                .collect(Collectors.groupingBy(
                        ParticipantProjection::eventId,
                        Collectors.mapping(
                                ParticipantProjection::profileImageUrl,
                                Collectors.toList())
                ));
    }

    private Map<UUID, Integer> getParticipantsCount(List<UUID> eventIds) {
        return startPointReader.findParticipantCounts(eventIds).stream()
                .collect(Collectors.toMap(
                        ParticipantCountProjection::eventId,
                        participants -> participants.count().intValue()
                ));
    }
}
