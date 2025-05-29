package com.meetup.server.review.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NonVisitedReasonCategory {
    NOISY("시끄러워서"),
    CONGESTION("사람이 너무 많아서"),
    DARKNESS("공간이 어두워서"),
    INSUFFICIENT_SEAT("좌석이 부족해서"),
    ETC("기타"),
    ;

    private final String description;

}
