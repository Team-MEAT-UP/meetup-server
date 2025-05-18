package com.meetup.server.place.domain.value;

import com.meetup.server.global.clients.google.place.search.GoogleSearchTextResponse;
import lombok.Builder;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Builder
public record OpeningHour(
        DayOfWeek day,
        LocalTime openTime,
        LocalTime closeTime
) {
    public static OpeningHour of(GoogleSearchTextResponse.Period period) {
        GoogleSearchTextResponse.OpenClose open = period.open();
        GoogleSearchTextResponse.OpenClose close = period.close();
        int day = open.day();

        return OpeningHour.builder()
                .day(DayOfWeek.of(day == 0 ? 7 : day)) // 0 = SUNDAY
                .openTime(LocalTime.of(open.hour(), open.minute()))
                .closeTime(LocalTime.of(close.hour(), close.minute()))
                .build();
    }
}
