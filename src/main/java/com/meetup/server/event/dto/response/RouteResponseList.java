package com.meetup.server.event.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RouteResponseList(
        int peopleCount,
        int averageTime,
        MeetingPoint meetingPoint,
        List<RouteResponse> routeResponse
) {


    public static RouteResponseList of(List<RouteResponse> routeResponse, MeetingPoint meetingPoint) {
        return RouteResponseList.builder()
                .averageTime(calculateAverageTime(routeResponse))
                .peopleCount(routeResponse.size())
                .meetingPoint(meetingPoint)
                .routeResponse(routeResponse)
                .build();
    }

    private static int calculateAverageTime(List<RouteResponse> routeResponse) {
        return routeResponse.stream()
                .mapToInt(RouteResponse::totalTime)
                .sum() / routeResponse.size();
    }
}
