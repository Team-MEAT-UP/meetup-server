package com.meetup.server.global.clients.util;

public interface RateLimiter {

    void tryApiCall(LimitRequestPerDay limitRequestPerDay);
}
