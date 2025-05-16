package com.meetup.server.startpoint.util;

import com.meetup.server.event.dto.response.DrivingRouteResponse;
import com.meetup.server.global.clients.odsay.OdsayTransitRouteSearchResponse;

import java.util.List;

public class RouteExtractor {

    public static int extractValidTransitTotalTime(OdsayTransitRouteSearchResponse transitResponse) {
        if (transitResponse == null || transitResponse.data() == null) {
            return 0;
        }

        var paths = transitResponse.data().path();
        if (paths == null || paths.isEmpty() || paths.getFirst() == null || paths.getFirst().info() == null) {
            return 0;
        }

        return paths.getFirst().info().totalTime();
    }

    public static int extractValidDrivingTotalTime(List<DrivingRouteResponse> drivingRoutes) {
        if (drivingRoutes == null || drivingRoutes.isEmpty() || drivingRoutes.getFirst() == null) {
            return 0;
        }
        return drivingRoutes.getFirst().duration();
    }
}
