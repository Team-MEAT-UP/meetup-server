package com.meetup.server.startpoint.persistence;

import com.meetup.server.startpoint.persistence.projection.ParticipantCountProjection;
import com.meetup.server.startpoint.persistence.projection.ParticipantProjection;
import com.meetup.server.startpoint.persistence.projection.EventHistoryProjection;

import java.util.List;
import java.util.UUID;

public interface StartPointCustomRepository {
    List<EventHistoryProjection> findUserEventProjections(Long userId, UUID lastViewedEventId, int size);

    List<ParticipantProjection> findParticipantInfosByEventIds(List<UUID> eventIds);

    List<ParticipantCountProjection> findParticipantCountsByEventIds(List<UUID> eventIds);
}
