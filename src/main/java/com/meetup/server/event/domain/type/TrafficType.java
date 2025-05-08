package com.meetup.server.event.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum TrafficType {
    SUBWAY(1),
    BUS(2),
    WALKING(3),
    ;

    private final int code;

    private static final Map<Integer, TrafficType> CODE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(TrafficType::getCode, Function.identity()));

    public static TrafficType fromCode(int code) {
        return CODE_MAP.get(code);
    }
}
