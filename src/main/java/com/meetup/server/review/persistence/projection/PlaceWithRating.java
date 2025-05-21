package com.meetup.server.review.persistence.projection;

import com.meetup.server.place.domain.Place;

public record PlaceWithRating(
        Place place,
        Double avgSocket,
        Double avgSeat,
        Double avgQuiet
) {
}
