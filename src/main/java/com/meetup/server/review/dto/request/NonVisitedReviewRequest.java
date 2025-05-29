package com.meetup.server.review.dto.request;

import com.meetup.server.review.domain.type.NonVisitedReasonCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record NonVisitedReviewRequest(

        @Schema(description = """
                방문하지 않은 이유 : NOISY(시끄러워서), CONGESTION(사람이 너무 많아서)
                DARKNESS(공간이 어두워서), INSUFFICIENT_SEAT(좌석이 부족해서)
                """, example = "[\"NOISY\", \"CONGESTION\"]", nullable = true)
        List<NonVisitedReasonCategory> categories,

        @Schema(description = "기타 사유 (직접 입력하기)", example = "느좋 카페 가고 싶어요", nullable = true)
        String etcReason,

        @Schema(description = "출발지명", example = "선정릉역 수인분당선", nullable = true)
        String placeName,

        @Schema(description = "지번주소", example = "서울특별시 강남구 삼성동 111-114", nullable = true)
        String address,

        @Schema(description = "도로명주소", example = "서울특별시 강남구 선릉로 지하580", nullable = true)
        String roadAddress,

        @Schema(description = "경도", example = "127.043999", nullable = true)
        Double longitude,

        @Schema(description = "위도", example = "37.510297", nullable = true)
        Double latitude
) {
}
