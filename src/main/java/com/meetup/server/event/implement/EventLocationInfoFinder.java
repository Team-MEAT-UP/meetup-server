package com.meetup.server.event.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.startpoint.domain.StartPoint;
import org.springframework.stereotype.Component;

@Component
public class EventLocationInfoFinder {

    public String findEndStationName(Event event) {
        return event.getSubway().getName();
    }

    public double findEndX(Event event) {
        return event.getSubway().getLocation().getRoadLongitude();
    }

    public double findEndY(Event event) {
        return event.getSubway().getLocation().getRoadLatitude();
    }

    public String findStartX(StartPoint startPoint) {
        return String.valueOf(startPoint.getLocation().getRoadLongitude());
    }

    public String findStartY(StartPoint startPoint) {
        return String.valueOf(startPoint.getLocation().getRoadLatitude());
    }
}
