package com.meetup.server.event.dto.response;

import com.meetup.server.parkinglot.persistence.projection.ClosestParkingLot;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.util.UsernameExtractor;
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

    public static RouteResponseList of(StartPoint startPoint, List<RouteResponse> routeResponse, MeetingPoint meetingPoint, ClosestParkingLot closestParkingLot) {
        return RouteResponseList.builder()
                .eventMaker(UsernameExtractor.extractDisplayName(startPoint))
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
