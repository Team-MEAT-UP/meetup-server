package com.meetup.server.global.util;

public record Coordinate(
        String x,
        String y
) {
    public static Coordinate of(double x, double y) {
        return new Coordinate(String.valueOf(x), String.valueOf(y));
    }
}
