package com.meetup.server.startpoint.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Location {

    @Column(name = "road_longitude", length = 255, nullable = false)
    private BigDecimal roadLongitude;   //경도

    @Column(name = "road_latitude", length = 255, nullable = false)
    private BigDecimal roadLatitude;    //위도
}
