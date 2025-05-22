package com.meetup.server.place.dto.response;

import com.meetup.server.review.domain.Review;
import lombok.Builder;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Builder
public record ReviewResponse(
        String nickname,
        String profileImage,
        LocalDate date,
        String day,
        String content
) {
    public static ReviewResponse from(Review review) {
        return ReviewResponse.builder()
                .nickname(review.getUser().getNickname())
                .profileImage(review.getUser().getProfileImage())
                .date(review.getCreatedAt().toLocalDate())
                .day(review.getCreatedAt().getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.KOREAN))
                .content(review.getVisitedReview().getContent())
                .build();
    }

    public static List<ReviewResponse> fromList(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewResponse::from)
                .toList();
    }
}
