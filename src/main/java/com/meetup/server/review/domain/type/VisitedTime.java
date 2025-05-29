package com.meetup.server.review.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VisitedTime {
    MORNING("아침"),
    LUNCH("점심"),
    NIGHT("저녁"),
    ;

    private final String description;
}
