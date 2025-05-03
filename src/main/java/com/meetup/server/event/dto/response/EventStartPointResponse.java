package com.meetup.server.event.dto.response;

import com.meetup.server.event.domain.Event;
import com.meetup.server.startpoint.domain.StartPoint;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
public record EventStartPointResponse(
        @Schema(description = "이벤트 ID", example = "01968fe2-5277-712a-ad3c-98f29c2782e0")
        UUID eventId,

        @Schema(description = "출발지 ID", example = "01968fe2-5277-712a-ad3c-98f29c2782e1")
        UUID startPointId,

        @Schema(description = "지번주소", example = "땡수팟")
        String username
) {
    public static EventStartPointResponse of(Event event, StartPoint startPoint, String username) {
        return EventStartPointResponse.builder()
                .eventId(event.getEventId())
                .startPointId(startPoint.getStartPointId())
                .username(username)
                .build();
    }
}
