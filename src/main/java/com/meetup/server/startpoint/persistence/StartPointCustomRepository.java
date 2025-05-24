package com.meetup.server.startpoint.persistence;

import com.meetup.server.startpoint.persistence.projection.ParticipantCount;
import com.meetup.server.startpoint.persistence.projection.Participant;
import com.meetup.server.startpoint.persistence.projection.EventHistory;

import java.util.List;
import java.util.UUID;

public interface StartPointCustomRepository {
    List<EventHistory> findEventHistories(Long userId, UUID lastViewedEventId, int size);

    List<Participant> findParticipantsWithImageUrls(List<UUID> eventIds);

    List<ParticipantCount> findParticipantsCounts(List<UUID> eventIds);
}
