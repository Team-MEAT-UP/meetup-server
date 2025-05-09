package com.meetup.server.parkinglot.implement;

import com.meetup.server.parkinglot.dto.ClosestParkingLot;
import com.meetup.server.parkinglot.persistence.ParkingLotRepository;
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
