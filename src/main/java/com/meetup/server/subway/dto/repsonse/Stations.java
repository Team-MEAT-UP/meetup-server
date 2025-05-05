package com.meetup.server.subway.dto.repsonse;

public record Stations(
        int index,  //순서
        String stationName,
        String x,  //경도(:x)
        String y  //위도(:y)
) {
}
