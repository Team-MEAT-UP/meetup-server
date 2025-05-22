package com.meetup.server.place.dto.response;

import com.meetup.server.place.domain.value.GoogleReview;
import lombok.Builder;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Builder
public record GoogleReviewResponse(
        String nickname,
        String profileImage,
        LocalDate date,
        String day,
        String content
) {
    public static GoogleReviewResponse from(GoogleReview googleReview) {
        return GoogleReviewResponse.builder()
                .nickname(googleReview.author())
                .profileImage(googleReview.authorProfileImage())
                .date(googleReview.publishTime().toLocalDate())
                .day(googleReview.publishTime().getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.KOREAN))
                .content(googleReview.content())
                .build();
    }

    public static List<GoogleReviewResponse> fromList(List<GoogleReview> googleReviews) {
        return googleReviews.stream()
                .map(GoogleReviewResponse::from)
                .toList();
    }
}
