package com.meetup.server.subway.exception;

import com.meetup.server.global.support.error.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SubwayErrorType implements ErrorType {
    ;

    private final HttpStatus status;

    private final String message;
}
