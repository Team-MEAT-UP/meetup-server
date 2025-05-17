package com.meetup.server.user.dto.response;

import java.util.List;

public record UserEventHistoryResponseList(
        List<UserEventHistoryResponse> userEventHistoryResponses
) {
}
