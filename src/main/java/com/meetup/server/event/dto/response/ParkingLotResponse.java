package com.meetup.server.event.dto.response;

import com.meetup.server.parkinglot.dto.ClosestParkingLot;
import lombok.Builder;

@Builder
public record ParkingLotResponse(
        String name,
        double distance
) {
    public static ParkingLotResponse from(ClosestParkingLot closestParkingLot) {
        return ParkingLotResponse.builder()
                .name(closestParkingLot.parkingLot().getName())
                .distance(closestParkingLot.distance())
                .build();
    }
}
