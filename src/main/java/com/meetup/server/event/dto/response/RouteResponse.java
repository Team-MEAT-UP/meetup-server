package com.meetup.server.event.dto.response;

import com.meetup.server.global.clients.kakao.mobility.KakaoMobilityResponse;
import com.meetup.server.global.clients.odsay.OdsayTransitRouteSearchResponse;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.user.domain.User;
import lombok.Builder;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
public record RouteResponse(
        boolean isTransit,  // true: 대중교통, false: 자동차
        UUID id,
        String nickname,
        String profileImage,
        String startName,  //출발지 주소
        double startLongitude,  //실제 출발지 경도(:longitude)
        double startLatitude,  //실제 출발지 위도(:latitude)
        List<TransitRouteResponse> transitRoute,
        List<DrivingRouteResponse> drivingRoute,
        int totalTime

) {
    public static RouteResponse of(StartPoint startPoint,
                                   User user,
                                   OdsayTransitRouteSearchResponse transitResponse,
                                   KakaoMobilityResponse drivingResponse,
                                   boolean isTransit) {
        return RouteResponse.builder()
                .isTransit(isTransit)
                .id(startPoint.getStartPointId())
                .nickname(startPoint.getIsUser() ? user.getNickname() : startPoint.getNonUserName())
                .profileImage(startPoint.getIsTransit() ? user.getProfileImage() : null)
                .startName(convertStartPointName(startPoint.getAddress().getAddress()))
                .startLongitude(startPoint.getLocation().getRoadLongitude())
                .startLatitude(startPoint.getLocation().getRoadLatitude())
                .transitRoute(TransitRouteResponse.from(transitResponse))
                .drivingRoute(DrivingRouteResponse.from(drivingResponse))
                .totalTime(isTransit ?
                        transitResponse.data().path().getFirst().info().totalTime()
                        : drivingResponse.routes().getFirst().summary().duration())
                .build();
    }

    private static String convertStartPointName(String address) {
        if (address == null || address.isBlank()) return "";

        Pattern pattern = Pattern.compile("(\\S+(구|군))\\s+(\\S+(동|읍))");
        Matcher matcher = pattern.matcher(address);

        if (matcher.find()) {
            return matcher.group(1) + " " + matcher.group(3);
        }
        return "";
    }
}
