package com.meetup.server.review.dto.request;

import com.meetup.server.review.domain.type.VisitedTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VisitedReviewRequest(

        @NotNull
        @Schema(description = "방문 시간 : MORNING(아침), LUNCH(점심), NIGHT(저녁)", example = "MORNING")
        VisitedTime visitedTime,

        @Min(value = 1) @Max(value = 5)
        @Schema(description = "콘센트 점수 (1 ~ 5)", example = "3")
        int socket,

        @Min(value = 1) @Max(value = 5)
        @Schema(description = "좌석 점수 (1 ~ 5)", example = "4")
        int seat,

        @Min(value = 1) @Max(value = 5)
        @Schema(description = "한산함 점수 (1 ~ 5)", example = "2")
        int quiet,

        @Schema(description = "방문 후기", example = "카공하기 좋은 느좋카페")
        String content
) {
}
