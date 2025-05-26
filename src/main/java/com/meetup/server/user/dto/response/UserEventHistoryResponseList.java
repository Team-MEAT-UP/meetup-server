package com.meetup.server.user.dto.response;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record UserEventHistoryResponseList(
        List<UserEventHistoryResponse> userEventHistoryResponses,
        boolean hasNextPage,
        UUID lastEventId
) {
    public static UserEventHistoryResponseList of(List<UserEventHistoryResponse> userEventHistoryResponses,
                                                  boolean hasNextPage,
                                                  UUID lastEventId
    ) {
        return UserEventHistoryResponseList.builder()
                .userEventHistoryResponses(userEventHistoryResponses)
                .hasNextPage(hasNextPage)
                .lastEventId(lastEventId)
                .build();
    }
}
