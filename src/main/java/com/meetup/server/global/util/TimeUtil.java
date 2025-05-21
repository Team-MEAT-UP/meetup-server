package com.meetup.server.global.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class TimeUtil {

    public static final ZoneId KST_ZONE_ID = ZoneId.of("Asia/Seoul");

    public static String calculateTimeAgo(LocalDateTime createdAt) {
        if (calculateDaysAgo(createdAt) >= 1) {
            return null;
        }

        int hours = calculateHoursAgo(createdAt);
        if (hours >= 1) {
            return hours + "시간 전";
        }

        int minutes = calculateMinutesAgo(createdAt);
        if (minutes >= 1) {
            return minutes + "분 전";
        }

        return "방금 전";
    }

    public static int calculateDaysAgo(LocalDateTime createdAt) {
        return (int) ChronoUnit.DAYS.between(createdAt, LocalDateTime.now(KST_ZONE_ID));
    }

    public static int calculateHoursAgo(LocalDateTime createdAt) {
        return (int) ChronoUnit.HOURS.between(createdAt, LocalDateTime.now(KST_ZONE_ID));
    }

    public static int calculateMinutesAgo(LocalDateTime createdAt) {
        return (int) ChronoUnit.MINUTES.between(createdAt, LocalDateTime.now(KST_ZONE_ID));
    }
}
