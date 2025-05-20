package com.meetup.server.place.exception;

import com.meetup.server.global.support.error.ErrorType;
import com.meetup.server.global.support.error.GlobalException;

public class PlaceException extends GlobalException {

    public PlaceException(ErrorType errorType) {
        super(errorType);
    }
}
