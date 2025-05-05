package com.meetup.server.event.dto.response;

import com.meetup.server.global.clients.kakao.mobility.KakaoMobilityResponse;
import com.meetup.server.global.clients.odsay.OdsayTransitRouteSearchResponse;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.user.domain.User;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record RouteResponse(
        UUID id,
        String nickname,
        String profileImage,
        String startStationName,  //출발지와 가장 가까운 역
        BigDecimal SX,  //역 경도(:longitude)
        BigDecimal SY,  //역 위도(:latitude)
        String startAddress,  //출발지 주소
        BigDecimal RX,  //실제 출발지 경도(:longitude)
        BigDecimal RY,  //실제 출발지 위도(:latitude)
        List<TransitRouteResponse> transitRoute,
        List<DrivingRouteResponse> drivingRoute,
        int totalTime,
        String middlePointStation,  //중간지점 역
        BigDecimal EX, //경도(:longitude)
        BigDecimal EY //위도(:latitude)
) {
    public static RouteResponse of(StartPoint startPoint,
                                   User user,
                                   OdsayTransitRouteSearchResponse transitResponse,
                                   KakaoMobilityResponse drivingResponse,
                                   boolean isTransit) {
        return RouteResponse.builder()
                .id(startPoint.getStartPointId())
                .nickname(startPoint.getIsUser() ? user.getNickname() : startPoint.getNonUserName())
                .profileImage(startPoint.getIsTransit() ? user.getProfileImage() : null)
                .startStationName(startPoint.getName())
                .SX(startPoint.getLocation().getRoadLatitude())
                .SY(startPoint.getLocation().getRoadLongitude())
                .startAddress(startPoint.getAddress().getRoadAddress())
                .RX(startPoint.getLocation().getRoadLatitude())
                .RY(startPoint.getLocation().getRoadLongitude())
                .transitRoute(TransitRouteResponse.of(transitResponse))
                .drivingRoute(DrivingRouteResponse.of(drivingResponse))
                .totalTime(isTransit ?
                        transitResponse.data().path().getFirst().info().totalTime()
                        : drivingResponse.routes().getFirst().summary().duration())
                .middlePointStation(startPoint.getEvent().getSubway().getName())
                .EX(startPoint.getEvent().getSubway().getLocation().getRoadLatitude())
                .EY(startPoint.getEvent().getSubway().getLocation().getRoadLongitude())
                .build();
    }
}
