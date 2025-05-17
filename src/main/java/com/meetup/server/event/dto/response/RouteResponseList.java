package com.meetup.server.event.dto.response;

import com.meetup.server.parkinglot.dto.ClosestParkingLot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponseList {

    private String eventMaker;
    private int peopleCount;
    private int averageTime;
    private MeetingPoint meetingPoint;
    private List<RouteResponse> routeResponse;
    private ParkingLotResponse parkingLot;

    public static RouteResponseList of(String eventMaker, List<RouteResponse> routeResponse, MeetingPoint meetingPoint, ClosestParkingLot closestParkingLot) {
        return RouteResponseList.builder()
                .eventMaker(eventMaker)
                .averageTime(calculateAverageTime(routeResponse))
                .peopleCount(routeResponse.size())
                .meetingPoint(meetingPoint)
                .routeResponse(routeResponse)
                .parkingLot(ParkingLotResponse.from(closestParkingLot))
                .build();
    }

    private static int calculateAverageTime(List<RouteResponse> routeResponse) {
        return routeResponse.stream()
                .mapToInt(RouteResponse::getTotalTime)
                .sum() / routeResponse.size();
    }

    public void updateRouteResponse(List<RouteResponse> routeResponse) {
        this.averageTime = calculateAverageTime(routeResponse);
        this.routeResponse = routeResponse;
    }
}
