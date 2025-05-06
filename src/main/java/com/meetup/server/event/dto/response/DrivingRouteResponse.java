package com.meetup.server.event.dto.response;

import com.meetup.server.global.clients.kakao.mobility.KakaoMobilityResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record DrivingRouteResponse(
        int taxi,   //요금
        int toll,   //통행료
        int duration, //총 이동 소요 시간(sec)
        int distance //총 이동 거리(m)
) {
    public static DrivingRouteResponse from(int taxi, int toll, int duration, int distance) {
        return DrivingRouteResponse.builder()
                .taxi(taxi)
                .toll(toll)
                .duration(durationConverter(duration))
                .distance(distance)
                .build();
    }

    public static List<DrivingRouteResponse> from(KakaoMobilityResponse drivingResponse) {
        if (drivingResponse.routes() == null || drivingResponse.routes().isEmpty()) {
            return List.of();
        }

        return drivingResponse.routes().stream()
                .map(route -> {
                    KakaoMobilityResponse.Summary summary = route.summary();
                    KakaoMobilityResponse.Fare fare = summary.fare();

                    return DrivingRouteResponse.from(
                            fare.taxi(),
                            fare.toll(),
                            summary.duration(),
                            summary.distance()
                    );
                })
                .toList();
    }

    public static int durationConverter(int duration) {
        return duration / 60;
    }
}
