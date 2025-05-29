package com.meetup.server.subway.exception;

import com.meetup.server.global.support.error.ErrorType;
import com.meetup.server.global.support.error.GlobalException;

public class SubwayException extends GlobalException {

    public SubwayException(ErrorType errorType) {
        super(errorType);
    }
}
