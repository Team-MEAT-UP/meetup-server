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
    public static GoogleReview of(GoogleSearchTextResponse.Review review) {
        LocalDateTime publishTime = OffsetDateTime.parse(review.publishTime())
                .atZoneSameInstant(ZoneId.systemDefault())
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
