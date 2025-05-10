package com.meetup.server.global.clients.kakao.local;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoLocalResponse(
        @JsonProperty("meta") KakaoSearchMeta kakaoSearchMeta,
        @JsonProperty("documents") KakaoSearchResponse[] kakaoSearchResponses
) {

    public record KakaoSearchMeta(
            @JsonProperty("total_count") Integer totalCount,
            @JsonProperty("pageable_count") Integer pageableCount,
            @JsonProperty("is_end") Boolean isEnd,
            @JsonProperty("same_name") SameName sameName
    ) {
        public record SameName(
                String[] region,
                String keyword,
                @JsonProperty("selected_region") String selectedRegion
        ) {
        }
    }

    public record KakaoSearchResponse(
            String id,
            @JsonProperty("place_name") String placeName,
            @JsonProperty("category_name") String categoryName,
            @JsonProperty("category_group_code") String categoryGroupCode,
            @JsonProperty("category_group_name") String categoryGroupName,
            String phone,
            @JsonProperty("address_name") String addressName,
            @JsonProperty("road_address_name") String roadAddressName,
            String x,
            String y,
            @JsonProperty("place_url") String placeUrl,
            String distance
    ) {
    }
}
