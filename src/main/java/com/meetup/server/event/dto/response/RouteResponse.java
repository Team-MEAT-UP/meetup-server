package com.meetup.server.event.dto.response;

import com.meetup.server.global.clients.kakao.mobility.KakaoMobilityResponse;
import com.meetup.server.global.clients.odsay.OdsayTransitRouteSearchResponse;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponse {

    private static final Pattern START_POINT_PATTERN = Pattern.compile("(\\S+(구|군))\\s+(\\S+(동|읍))");

    private Boolean isTransit;  // true: 대중교통, false: 자동차
    private Boolean isMe;
    private UUID id;
    private Long userId;
    private UUID guestId;
    private String nickname;
    private String profileImage;
    private String startName;  // 출발지 주소
    private double startLongitude;  // 실제 출발지 경도(:longitude)
    private double startLatitude;  // 실제 출발지 위도(:latitude)
    private List<TransitRouteResponse> transitRoute;
    private DrivingInfoResponse drivingInfo;
    private List<DrivingRouteResponse> drivingRoute;
    private int totalTime;
    private int transitTime;
    private int driveTime;

    public static RouteResponse of(StartPoint startPoint,
                                   User user,
                                   OdsayTransitRouteSearchResponse transitResponse,
                                   KakaoMobilityResponse drivingResponse,
                                   int transitTime,
                                   int driveTime) {
        return RouteResponse.builder()
                .isTransit(startPoint.isTransit())
                .isMe(false)
                .id(startPoint.getStartPointId())
                .userId(startPoint.getIsUser() ? user.getUserId() : null)
                .guestId(startPoint.getGuestId())
                .nickname(startPoint.getIsUser() ? user.getNickname() : startPoint.getNonUserName())
                .profileImage(startPoint.getIsUser() ? user.getProfileImage() : null)
                .startName(convertStartPointName(startPoint.getAddress().getAddress()))
                .startLongitude(startPoint.getLocation().getRoadLongitude())
                .startLatitude(startPoint.getLocation().getRoadLatitude())
                .transitRoute(TransitRouteResponse.from(transitResponse))
                .drivingInfo(DrivingInfoResponse.from(drivingResponse))
                .drivingRoute(DrivingRouteResponse.from(drivingResponse))
                .totalTime(startPoint.isTransit() ? transitTime : driveTime)
                .transitTime(transitTime)
                .driveTime(driveTime)
                .build();
    }

    private static String convertStartPointName(String address) {
        if (address == null || address.isBlank()) return "";

        Matcher matcher = START_POINT_PATTERN.matcher(address);
        if (matcher.find()) {
            return matcher.group(1) + " " + matcher.group(3);
        }
        return "";
    }

    public void updateIsTransit(boolean isTransit) {
        if (isTransit) {
            this.totalTime = transitTime;
        } else {
            this.totalTime = driveTime;
        }
        this.isTransit = isTransit;
    }

    public void updateIsMe(boolean isMe) {
        this.isMe = isMe;
    }
}
