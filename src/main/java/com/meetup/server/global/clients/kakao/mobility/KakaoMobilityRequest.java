package com.meetup.server.global.clients.kakao.mobility;

import lombok.Builder;

@Builder
public record KakaoMobilityRequest(
        String origin,
        String destination,
        String waypoints,
        String priority,
        String avoid,
        Integer roadEvent,
        Boolean alternatives,
        Boolean roadDetails,
        Integer carType,
        String carFuel,
        Boolean carHiPass,
        Boolean summary
) {
//    @Builder
//    public record Origin(
//            String x,
//            String y
//    ) {
//    }
//
//    @Builder
//    public record Destination(
//            String x,
//            String y
//    ) {
//    }
}
