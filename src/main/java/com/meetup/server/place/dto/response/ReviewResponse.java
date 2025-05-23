package com.meetup.server.place.dto.response;

import com.meetup.server.review.domain.Review;
import com.meetup.server.review.domain.VisitedReview;
import com.meetup.server.user.domain.User;
import lombok.Builder;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
                .nickname(Optional.ofNullable(review.getUser())
                        .map(User::getNickname)
                        .orElse(null))
                .profileImage(Optional.ofNullable(review.getUser())
                        .map(User::getProfileImage)
                        .orElse(null))
                .date(review.getCreatedAt().toLocalDate())
                .day(review.getCreatedAt().getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.KOREAN))
                .content(Optional.ofNullable(review.getVisitedReview())
                        .map(VisitedReview::getContent)
                        .orElse(null))
                .build();
    }

    public static List<ReviewResponse> fromList(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewResponse::from)
                .toList();
    }
}
