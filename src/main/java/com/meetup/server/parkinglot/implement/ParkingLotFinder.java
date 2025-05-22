package com.meetup.server.parkinglot.implement;

import com.meetup.server.parkinglot.persistence.ParkingLotRepository;
import com.meetup.server.parkinglot.persistence.projection.ClosestParkingLot;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParkingLotFinder {

    private final ParkingLotRepository parkingLotRepository;

    public ClosestParkingLot findClosestParkingLot(Point point) {
        return parkingLotRepository.findClosestParkingLot(point);
    }
}
