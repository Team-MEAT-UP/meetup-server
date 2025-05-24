package com.meetup.server.startpoint.persistence;

import com.meetup.server.startpoint.persistence.projection.EventHistory;
import com.meetup.server.startpoint.persistence.projection.ParticipantCount;
import com.meetup.server.startpoint.persistence.projection.Participant;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.meetup.server.event.domain.QEvent.event;
import static com.meetup.server.startpoint.domain.QStartPoint.startPoint;

@Repository
@RequiredArgsConstructor
public class StartPointCustomRepositoryImpl implements StartPointCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<EventHistory> findEventHistories(Long userId, UUID lastViewedEventId, int size) {
        LocalDateTime lastViewedTime;
        BooleanExpression cursor = null;

        if (lastViewedEventId != null) {
            lastViewedTime = jpaQueryFactory
                    .select(event.createdAt)
                    .from(event)
                    .where(event.eventId.eq(lastViewedEventId))
                    .fetchOne();

            if (lastViewedTime != null) {
                cursor = event.createdAt.lt(lastViewedTime)
                        .or(event.createdAt.eq(lastViewedTime).and(event.eventId.lt(lastViewedEventId)));
            }
        }

        BooleanExpression isUser = startPoint.user.userId.eq(userId);
        if (cursor != null) {
            cursor = isUser.and(cursor);
        }

        return jpaQueryFactory
                .select(Projections.constructor(
                        EventHistory.class,
                        event.eventId,
                        event.place.id,
                        event.subway.name,
                        event.place.name,
                        event.createdAt
                ))
                .from(startPoint)
                .join(startPoint.event, event)
                .leftJoin(event.place)
                .leftJoin(event.subway)
                .where(cursor)
                .orderBy(event.createdAt.desc(), event.eventId.desc())
                .distinct()
                .limit(size)
                .fetch();
    }

    @Override
    public List<Participant> findParticipantsWithImageUrls(List<UUID> eventIds) {
        return jpaQueryFactory
                .select(Projections.constructor(Participant.class,
                        startPoint.event.eventId,
                        startPoint.user.profileImage))
                .from(startPoint)
                .where(startPoint.event.eventId.in(eventIds))
                .fetch();
    }

    @Override
    public List<ParticipantCount> findParticipantsCounts(List<UUID> eventIds) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        ParticipantCount.class,
                        startPoint.event.eventId,
                        startPoint.count()
                ))
                .from(startPoint)
                .where(startPoint.event.eventId.in(eventIds))
                .groupBy(startPoint.event.eventId)
                .fetch();
    }
}
