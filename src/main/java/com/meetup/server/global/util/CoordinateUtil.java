package com.meetup.server.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoordinateUtil {

    private static final double EARTH_RADIUS_KM = 6371.0;

    public static Point createPoint(double longitude, double latitude) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate = new Coordinate(longitude, latitude);
        return geometryFactory.createPoint(coordinate);
    }

    public static Point calculateCenterPoint(List<Point> points) {
        if (points == null || points.isEmpty()) {
            throw new IllegalArgumentException("중간 좌표를 계산할 좌표 리스트가 존재하지 않습니다.");
        }

        if (points.size() == 1) {
            return points.getFirst();
        }

        double sumLongitude = 0.0;
        double sumLatitude = 0.0;

        for (Point point : points) {
            sumLongitude += point.getX();
            sumLatitude += point.getY();
        }

        double avgLongitude = sumLongitude / points.size();
        double avgLatitude = sumLatitude / points.size();

        return createPoint(avgLongitude, avgLatitude);
    }

    public static double calculateDistance(Point start, Point end) {
        double startLongitude = Math.toRadians(start.getX());
        double startLatitude = Math.toRadians(start.getY());
        double endLongitude = Math.toRadians(end.getX());
        double endLatitude = Math.toRadians(end.getY());

        double deltaLongitude = endLongitude - startLongitude;
        double deltaLatitude = endLatitude - startLatitude;

        double a = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2) +
                Math.cos(startLatitude) * Math.cos(endLatitude) * Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}
