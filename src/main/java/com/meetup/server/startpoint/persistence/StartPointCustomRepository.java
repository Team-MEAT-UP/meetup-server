package com.meetup.server.startpoint.persistence;

import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.user.dto.response.UserEventHistoryProjection;

import java.util.List;
import java.util.UUID;

public interface StartPointCustomRepository {
    List<UserEventHistoryProjection> findUserEventProjections(Long userId, UUID lastViewedEventId, int size);
    List<StartPoint> findParticipantsByEventIds(List<UUID> eventIds);
}
