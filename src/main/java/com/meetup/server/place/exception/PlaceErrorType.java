package com.meetup.server.place.exception;

import com.meetup.server.global.support.error.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PlaceErrorType implements ErrorType {
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "장소를 찾을 수 없습니다."),
    ;

    private final HttpStatus status;

    private final String message;
}
