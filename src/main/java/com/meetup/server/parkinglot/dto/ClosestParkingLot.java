package com.meetup.server.parkinglot.dto;

import com.meetup.server.parkinglot.domain.ParkingLot;

public record ClosestParkingLot(
        ParkingLot parkingLot,
        double distance
) {
}
