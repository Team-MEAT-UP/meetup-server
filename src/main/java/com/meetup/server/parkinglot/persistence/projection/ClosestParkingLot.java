package com.meetup.server.parkinglot.persistence.projection;

import com.meetup.server.parkinglot.domain.ParkingLot;

public record ClosestParkingLot(
        ParkingLot parkingLot,
        double distance
) {
}
