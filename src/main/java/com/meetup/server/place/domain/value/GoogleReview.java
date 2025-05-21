package com.meetup.server.place.domain.value;

import com.meetup.server.global.clients.google.place.search.GoogleSearchTextResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static com.meetup.server.global.util.TimeUtil.KST_ZONE_ID;

@Builder
public record GoogleReview(
        Integer rating,
        String content,
        String author,
        String authorProfileImage,
        LocalDateTime publishTime
) {
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
