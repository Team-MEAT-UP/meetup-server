package com.meetup.server.event.exception;

import com.meetup.server.global.support.error.ErrorType;
import com.meetup.server.global.support.error.GlobalException;

public class EventException extends GlobalException {

    public EventException(ErrorType errorType) {
        super(errorType);
    }
}
