package com.meetup.server.place.dto.response;

import com.meetup.server.subway.domain.Subway;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PlaceResponseList(
        @Schema(description = "중간지점명", example = "선정릉")
        String middlePointName,

        @Schema(description = "중간지점 지하철역 ID", example = "1")
        Integer subwayId,

        @Schema(description = "확정 장소")
        PlaceResponse confirmedPlaceResponse,

        @Schema(description = "장소 추천 리스트")
        List<PlaceResponse> placeResponses
) {
    public static PlaceResponseList of(Subway subway, PlaceResponse confirmedPlaceResponse, List<PlaceResponse> placeResponses) {
        return new PlaceResponseList(subway.getName(), subway.getSubwayId(), confirmedPlaceResponse, placeResponses);
    }
}
