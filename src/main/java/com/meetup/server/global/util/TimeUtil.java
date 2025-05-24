package com.meetup.server.global.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class TimeUtil {

    public static final ZoneId KST_ZONE_ID = ZoneId.of("Asia/Seoul");

    public static int calculateDaysAgo(LocalDateTime createdAt) {
        return (int) ChronoUnit.DAYS.between(createdAt, LocalDateTime.now(KST_ZONE_ID));
    }

    public static int calculateHoursAgo(LocalDateTime createdAt) {
        return (int) ChronoUnit.HOURS.between(createdAt, LocalDateTime.now(KST_ZONE_ID));
    }
}
