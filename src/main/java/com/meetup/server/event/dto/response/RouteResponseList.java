package com.meetup.server.event.dto.response;

import com.meetup.server.parkinglot.dto.ClosestParkingLot;
import lombok.Builder;

import java.util.List;

@Builder
public record RouteResponseList(
        int peopleCount,
        int averageTime,
        MeetingPoint meetingPoint,
        List<RouteResponse> routeResponse,
        ParkingLotResponse parkingLot
) {

    public static RouteResponseList of(List<RouteResponse> routeResponse, MeetingPoint meetingPoint, ClosestParkingLot closestParkingLot) {
        return RouteResponseList.builder()
                .averageTime(calculateAverageTime(routeResponse))
                .peopleCount(routeResponse.size())
                .meetingPoint(meetingPoint)
                .routeResponse(routeResponse)
                .parkingLot(ParkingLotResponse.from(closestParkingLot))
                .build();
    }

    private static int calculateAverageTime(List<RouteResponse> routeResponse) {
        return routeResponse.stream()
                .mapToInt(RouteResponse::totalTime)
                .sum() / routeResponse.size();
    }
}
