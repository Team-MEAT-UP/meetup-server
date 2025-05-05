package com.meetup.server.event.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RouteResponseList(
        List<RouteResponse> routeResponse,
        int peopleCount,
        int averageTime,
        boolean isTransit   // true: 대중교통, false: 자동차
) {
    public static RouteResponseList of(List<RouteResponse> routeResponse, boolean isTransit) {
        return RouteResponseList.builder()
                .routeResponse(routeResponse)
                .averageTime(calculateAverageTime(routeResponse))
                .isTransit(isTransit)
                .peopleCount(routeResponse.size())
                .build();
    }

    private static int calculateAverageTime(List<RouteResponse> routeResponse) {
        return routeResponse.stream()
                .mapToInt(RouteResponse::totalTime)
                .sum() / routeResponse.size();
    }
}
