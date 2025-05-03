package com.meetup.server.startpoint.persistence;

import com.meetup.server.event.domain.Event;
import com.meetup.server.startpoint.domain.StartPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StartPointRepository extends JpaRepository<StartPoint, UUID> {

    int countByEvent(Event event);
}
