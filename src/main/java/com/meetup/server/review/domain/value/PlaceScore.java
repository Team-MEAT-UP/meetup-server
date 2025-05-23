package com.meetup.server.review.domain.value;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PlaceScore {

    private int socket;

    private int seat;

    private int quiet;

    public static PlaceScore of(int socket, int seat, int quiet) {
        return new PlaceScore(socket, seat, quiet);
    }

    public static PlaceScore fromRoundedAverages(double socket, double seat, double quiet) {
        return PlaceScore.of(
                (int) Math.round(socket),
                (int) Math.round(seat),
                (int) Math.round(quiet)
        );
    }
}
