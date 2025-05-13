package com.meetup.server.startpoint.implement;

import com.meetup.server.event.domain.Event;
import com.meetup.server.event.implement.EventValidator;
import com.meetup.server.global.util.CoordinateUtil;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.startpoint.domain.type.Address;
import com.meetup.server.startpoint.domain.type.Location;
import com.meetup.server.startpoint.dto.request.StartPointRequest;
import com.meetup.server.startpoint.persistence.StartPointRepository;
import com.meetup.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartPointProcessor {

    private final StartPointRepository startPointRepository;
    private final EventValidator eventValidator;

    public StartPoint save(Event event, User user, StartPointRequest startPointRequest) {
        eventValidator.validateEventIsNotFull(event);
        StartPoint startPoint = StartPoint.builder()
                .event(event)
                .user(user)
                .name(startPointRequest.startPoint())
                .address(Address.of(startPointRequest.address(), startPointRequest.roadAddress()))
                .location(Location.of(startPointRequest.longitude(), startPointRequest.latitude()))
                .point(CoordinateUtil.createPoint(startPointRequest.longitude(), startPointRequest.latitude()))
                .isUser(user != null)
                .nonUserName(startPointRequest.username())
                .build();

        return startPointRepository.save(startPoint);
    }

    public void updateTransit(StartPoint startPoint, boolean isTransit) {
        startPoint.updateIsTransit(isTransit);
    }
}
