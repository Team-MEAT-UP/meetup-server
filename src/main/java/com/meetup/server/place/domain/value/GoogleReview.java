package com.meetup.server.place.domain.value;

import com.meetup.server.global.clients.google.place.search.GoogleSearchTextResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Builder
public record GoogleReview(
        Integer rating,
        String content,
        String author,
        String authorProfileImage,
        LocalDateTime publishTime
) {
    private static final ZoneId KST_ZONE_ID = ZoneId.of("Asia/Seoul");

    public static GoogleReview from(GoogleSearchTextResponse.Review review) {
        LocalDateTime publishTime = OffsetDateTime.parse(review.publishTime())
                .atZoneSameInstant(KST_ZONE_ID)
                .toLocalDateTime();

        return GoogleReview.builder()
                .rating(review.rating())
                .content(review.originalText().text())
                .author(review.authorAttribution().displayName())
                .authorProfileImage(review.authorAttribution().photoUri())
                .publishTime(publishTime)
                .build();
    }
}
