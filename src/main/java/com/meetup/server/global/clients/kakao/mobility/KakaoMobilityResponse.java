package com.meetup.server.global.clients.kakao.mobility;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoMobilityResponse(
        @JsonProperty("trans_id") String transId,
        Route[] routes
) {
    public record Route(
            @JsonProperty("result_code") int resultCode,
            @JsonProperty("result_msg") String resultMsg,
            Summary summary,
            Section[] sections
    ) {
    }

    public record Summary(
            Point origin,
            Point destination,
            Point[] waypoints,
            String priority,
            Bound bound,
            Fare fare,
            Integer distance,
            Integer duration
    ) {
    }

    public record Point(
            String name,
            double x,
            double y
    ) {
    }

    public record Bound(
            @JsonProperty("min_x") double minX,
            @JsonProperty("min_y") double minY,
            @JsonProperty("max_x") double maxX,
            @JsonProperty("max_y") double maxY
    ) {
    }

    public record Fare(
            int taxi,
            int toll
    ) {
    }

    public record Section(
            int distance,
            int duration,
            Bound bound,
            Road[] roads,
            Guide[] guides
    ) {
    }

    public record Road(
            String name,
            int distance,
            int duration,
            @JsonProperty("traffic_speed") double trafficSpeed,
            @JsonProperty("traffic_state") int trafficState,
            double[] vertexes
    ) {
    }

    public record Guide(
            String name,
            double x,
            double y,
            int distance,
            int duration,
            int type,
            String guidance,
            @JsonProperty("road_index") int roadIndex
    ) {
    }
}
