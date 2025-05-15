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
        Boolean summary,
        String opt
) {
}
