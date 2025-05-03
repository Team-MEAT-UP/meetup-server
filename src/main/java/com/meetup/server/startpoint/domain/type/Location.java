package com.meetup.server.startpoint.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Location {

    @Column(name = "road_longitude", nullable = false, columnDefinition = "NUMERIC(9,6)")
    private BigDecimal roadLongitude;   //경도

    @Column(name = "road_latitude", nullable = false, columnDefinition = "NUMERIC(9,6)")
    private BigDecimal roadLatitude;    //위도

    public static Location of(BigDecimal longitude, BigDecimal latitude) {
        return new Location(longitude, latitude);
    }
}
