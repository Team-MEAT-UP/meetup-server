package com.meetup.server.review.presentation;

import com.meetup.server.global.support.response.ApiResponse;
import com.meetup.server.review.application.ReviewService;
import com.meetup.server.review.dto.request.NonVisitedReviewRequest;
import com.meetup.server.review.dto.request.VisitedReviewRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Review API", description = "리뷰 API")
@RestController
@RequestMapping("/places/{placeId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "확정 장소 방문 리뷰 작성 API", description = "확정 장소를 방문한 사용자의 리뷰를 작성합니다")
    @PostMapping("/visited")
    public ApiResponse<?> createVisitedReview(
            @PathVariable("placeId") UUID placeId,
            @AuthenticationPrincipal Long userId,
            @RequestParam(value = "eventId") UUID eventId,
            @Valid @RequestBody VisitedReviewRequest visitedReviewRequest) {

        reviewService.createVisitedReview(eventId, placeId, userId, visitedReviewRequest);
        return ApiResponse.success();
    }

    @Operation(summary = "확정 장소 미방문 리뷰 작성 API", description = "확정 장소를 미방문한 사용자의 리뷰를 작성합니다")
    @PostMapping("/non-visited")
    public ApiResponse<?> createNonVisitedReview(
            @PathVariable("placeId") UUID placeId,
            @AuthenticationPrincipal Long userId,
            @RequestParam(value = "eventId") UUID eventId,
            @Valid @RequestBody NonVisitedReviewRequest nonVisitedReviewRequest) {

        reviewService.createNonVisitedReview(eventId, placeId, userId, nonVisitedReviewRequest);
        return ApiResponse.success();
    }
}
