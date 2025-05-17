package com.meetup.server.startpoint.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KakaoMobilityResultCode {

    SUCCESS(0),
    NOT_FOUND(1),
    NOT_FOUND_ROUTE_NEARBY_START_POINT(102),
    NOT_FOUND_ROUTE_NEARBY_END_POINT(103),
    NOT_FOUND_SHORT_DISTANCE(104),
    DISABLED_ROUTE_NEARBY_START_POINT(105),
    DISABLED_ROUTE_NEARBY_END_POINT(106),
    ;

    private final int code;

    public boolean matches(int code) {
        return this.code == code;
    }
}
