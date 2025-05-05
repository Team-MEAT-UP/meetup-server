package com.meetup.server.subway.dto.repsonse;

import java.util.List;

public record PassStopList(
        List<Stations> stations
) {
}
