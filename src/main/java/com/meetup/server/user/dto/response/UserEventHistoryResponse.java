package com.meetup.server.user.dto.response;

import com.meetup.server.event.domain.Event;
import com.meetup.server.global.util.TimeUtil;
import com.meetup.server.place.domain.Place;
import com.meetup.server.subway.domain.Subway;
import com.meetup.server.user.domain.User;
import lombok.Builder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder
public record UserEventHistoryResponse(
        UUID eventId,
        String middlePointName,
        String placeName,
        int participatedPeopleCount,
        List<String> userProfileImageUrls,
        int eventMadeAgo,
        String eventTimeAgo,
        boolean isReviewed
) {
    public static UserEventHistoryResponse of(List<User> userList, Event event, int participatedPeopleCount, boolean isReviewed) {
        return UserEventHistoryResponse.builder()
                .eventId(event.getEventId())
                .middlePointName(Optional.ofNullable(event.getSubway())
                        .map(Subway::getName)
                        .orElse(null))
                .placeName(Optional.ofNullable(event.getPlace())
                        .map(Place::getName)
                        .orElse(null))
                .participatedPeopleCount(participatedPeopleCount)
                .userProfileImageUrls(userList.stream()
                        .map(User::getProfileImage)
                        .toList())
                .eventMadeAgo(TimeUtil.calculateDaysAgo(event.getCreatedAt()))
                .eventTimeAgo(TimeUtil.calculateTimeAgo(event.getCreatedAt()))
                .isReviewed(isReviewed)
                .build();
    }
}
