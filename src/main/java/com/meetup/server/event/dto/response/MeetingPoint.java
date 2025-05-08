package com.meetup.server.event.dto.response;

public record MeetingPoint(
        String endStationName,  //중간지점 역
        double endLongitude, //경도(:longitude)
        double endLatitude //위도(:latitude)
) {
    public static MeetingPoint of(String endStationName, double endLongitude, double endLatitude) {
        return new MeetingPoint(endStationName, endLongitude, endLatitude);
    }
}
