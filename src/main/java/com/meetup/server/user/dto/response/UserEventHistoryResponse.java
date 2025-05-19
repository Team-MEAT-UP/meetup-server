package com.meetup.server.user.dto.response;

import com.meetup.server.event.domain.Event;
import com.meetup.server.global.util.TimeUtil;
import com.meetup.server.user.domain.User;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record UserEventHistoryResponse(
        UUID eventId,
        String middlePointName,
        String placeName,
        int participatedPeopleCount,
        List<String> userProfileImageUrls,
        int eventMadeAgo,
        boolean isReviewed
) {
    public static UserEventHistoryResponse of(List<User> userList, Event event, int participatedPeopleCount, boolean isReviewed) {
        return UserEventHistoryResponse.builder()
                .eventId(event.getEventId())
                .middlePointName(event.getSubway().getName())
                .placeName(event.getPlace() != null ? event.getPlace().getName() : null)
                .participatedPeopleCount(participatedPeopleCount)
                .userProfileImageUrls(userList.stream()
                        .map(User::getProfileImage)
                        .toList())
                .eventMadeAgo(TimeUtil.calculateDaysAgo(event.getCreatedAt()))
                .isReviewed(isReviewed)
                .build();
    }
}
