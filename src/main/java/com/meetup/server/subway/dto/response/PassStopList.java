package com.meetup.server.subway.dto.response;

import java.util.List;

public record PassStopList(
        List<Stations> stations
) {
}
