package com.meetup.server.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

    public static String truncate(String value, int maxLength) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return value.length() <= maxLength ? value : value.substring(0, maxLength);
    }
}
