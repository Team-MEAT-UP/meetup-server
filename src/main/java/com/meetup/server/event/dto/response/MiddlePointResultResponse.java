package com.meetup.server.event.dto.response;

import com.meetup.server.event.domain.Event;
import com.meetup.server.startpoint.domain.StartPoint;
import lombok.Builder;

import java.util.List;

@Builder
public record MiddlePointResultResponse(
        Event event,
        List<StartPoint> startPoints
) {
    public static MiddlePointResultResponse of(Event event, List<StartPoint> startPoints) {
        return MiddlePointResultResponse.builder()
                .event(event)
                .startPoints(startPoints)
                .build();
    }
}

