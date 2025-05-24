package com.meetup.server.startpoint.persistence;

import com.meetup.server.startpoint.persistence.projection.ParticipantCountProjection;
import com.meetup.server.startpoint.persistence.projection.ParticipantProjection;
import com.meetup.server.startpoint.persistence.projection.EventHistory;

import java.util.List;
import java.util.UUID;

public interface StartPointCustomRepository {
    List<EventHistory> findEventHistories(Long userId, UUID lastViewedEventId, int size);

    List<ParticipantProjection> findParticipantsWithImageUrls(List<UUID> eventIds);

    List<ParticipantCountProjection> findParticipantsCounts(List<UUID> eventIds);
}
