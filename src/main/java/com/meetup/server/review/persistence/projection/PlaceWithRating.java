package com.meetup.server.review.persistence.projection;

import com.meetup.server.place.domain.Place;

import java.util.Objects;
import java.util.stream.Stream;

public record PlaceWithRating(
        Place place,
        Double avgSocket,
        Double avgSeat,
        Double avgQuiet
) {

    public double calculateAverageRating() {
        double averageRating = Stream.of(this.avgSocket(), this.avgSeat(), this.avgQuiet())
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        return Math.round(averageRating * 10.0) / 10.0;
    }
}
