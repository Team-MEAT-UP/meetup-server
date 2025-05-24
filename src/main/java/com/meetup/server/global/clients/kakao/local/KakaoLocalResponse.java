package com.meetup.server.global.clients.kakao.local;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoLocalResponse {
    @JsonProperty("meta")
    private KakaoSearchMeta kakaoSearchMeta;
    @JsonProperty("documents")
    private List<KakaoSearchResponse> kakaoSearchResponses;

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KakaoSearchMeta {
        private Integer totalCount;
        private Integer pageableCount;
        private Boolean isEnd;
        private SameName sameName;

        @Getter
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class SameName {
            private List<String> region;
            private String keyword;
            private String selectedRegion;
        }
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KakaoSearchResponse {
        private String id;
        private String placeName;
        private String categoryName;
        private String categoryGroupCode;
        private String categoryGroupName;
        private String phone;
        private String addressName;
        private String roadAddressName;
        private String x;
        private String y;
        private String placeUrl;
        private String distance;
    }

    public void updateKakaoSearchResponse(List<KakaoSearchResponse> kakaoSearchResponses) {
        this.kakaoSearchResponses = kakaoSearchResponses;
    }
}
