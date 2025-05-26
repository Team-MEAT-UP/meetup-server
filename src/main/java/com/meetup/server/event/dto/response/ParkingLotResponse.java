package com.meetup.server.event.dto.response;

import com.meetup.server.parkinglot.domain.ParkingLot;
import com.meetup.server.parkinglot.persistence.projection.ClosestParkingLot;
import lombok.Builder;

@Builder
public record ParkingLotResponse(
        String name,
        double longitude,
        double latitude,
        double distance
) {
    public static ParkingLotResponse from(ClosestParkingLot closestParkingLot) {
        ParkingLot parkingLot = closestParkingLot.parkingLot();
        return ParkingLotResponse.builder()
                .name(parkingLot.getName())
                .longitude(parkingLot.getLocation().getRoadLongitude())
                .latitude(parkingLot.getLocation().getRoadLatitude())
                .distance(closestParkingLot.distance())
                .build();
    }
}
