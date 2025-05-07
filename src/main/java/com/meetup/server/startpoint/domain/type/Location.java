package com.meetup.server.startpoint.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Location {

    @Column(name = "road_longitude", nullable = false)
    private double roadLongitude;   //경도

    @Column(name = "road_latitude", nullable = false)
    private double roadLatitude;    //위도

    public static Location of(double longitude, double latitude) {
        return new Location(longitude, latitude);
    }
}
