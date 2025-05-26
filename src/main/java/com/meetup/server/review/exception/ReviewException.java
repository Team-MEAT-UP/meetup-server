package com.meetup.server.review.exception;

import com.meetup.server.global.support.error.ErrorType;
import com.meetup.server.global.support.error.GlobalException;

public class ReviewException extends GlobalException {

    public ReviewException(ErrorType errorType) {
        super(errorType);
    }
}
