package com.meetup.server.event.exception;

import com.meetup.server.global.support.error.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EventErrorType implements ErrorType {
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "모임을 찾을 수 없습니다."),
    START_POINT_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "출발지는 최대 8개까지 등록할 수 있습니다.")
    ;

    private final HttpStatus status;

    private final String message;
}
