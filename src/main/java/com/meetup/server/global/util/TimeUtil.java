package com.meetup.server.global.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtil {
    public static int calculateDaysAgo(LocalDateTime createdAt) {
        return (int) ChronoUnit.DAYS.between(createdAt, LocalDateTime.now());
    }
}
