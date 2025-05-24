package com.meetup.server.review.persistence.projection;

public record PlaceQuietnessWithRating(
        Double morning,
        Double lunch,
        Double night
) {
}
