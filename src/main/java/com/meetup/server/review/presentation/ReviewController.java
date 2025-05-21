package com.meetup.server.review.presentation;

import com.meetup.server.global.support.response.ApiResponse;
import com.meetup.server.review.dto.request.VisitedReviewRequest;
import com.meetup.server.review.application.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Review API", description = "리뷰 API")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "확정 장소 방문 리뷰 작성 API", description = "확정 장소를 방문한 사용자의 리뷰를 작성합니다")
    @PostMapping("/places/{placeId}/visited")
    public ApiResponse<?> createVisitedReview(
            @PathVariable("placeId") UUID placeId,
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody VisitedReviewRequest visitedReviewRequest) {

        reviewService.createVisitedReview(placeId, userId, visitedReviewRequest);
        return ApiResponse.success();
    }
}
