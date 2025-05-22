package com.meetup.server.startpoint.persistence;

import com.meetup.server.event.domain.QEvent;
import com.meetup.server.startpoint.domain.QStartPoint;
import com.meetup.server.startpoint.domain.StartPoint;
import com.meetup.server.user.domain.QUser;
import com.meetup.server.user.dto.response.UserEventHistoryProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StartPointCustomRepositoryImpl implements StartPointCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QStartPoint sp = QStartPoint.startPoint;
    private final QEvent event = QEvent.event;
    private final QUser user = QUser.user;

    @Override
    public List<UserEventHistoryProjection> findUserEventProjections(Long userId, UUID lastViewedEventId, int size) {
        LocalDateTime lastViewedCreatedAt = null;
        BooleanExpression cursorCondition = null;

        if (lastViewedEventId != null) {
            lastViewedCreatedAt = jpaQueryFactory
                    .select(event.createdAt)
                    .from(event)
                    .where(event.eventId.eq(lastViewedEventId))
                    .fetchOne();

            if (lastViewedCreatedAt != null) {
                cursorCondition = event.createdAt.lt(lastViewedCreatedAt)
                        .or(event.createdAt.eq(lastViewedCreatedAt).and(event.eventId.lt(lastViewedEventId)));
            }
        }

        BooleanExpression predicate = sp.user.userId.eq(userId);
        if (cursorCondition != null) {
            predicate = predicate.and(cursorCondition);
        }

        return jpaQueryFactory
                .select(Projections.constructor(
                        UserEventHistoryProjection.class,
                        event.eventId,
                        event.place.id,
                        event.subway.name,
                        event.place.name,
                        event.createdAt
                ))
                .from(sp)
                .join(sp.event, event)
                .where(predicate)
                .orderBy(event.createdAt.desc(), event.eventId.desc())
                .distinct()
                .limit(size)
                .fetch();
    }

    @Override
    public List<StartPoint> findParticipantsByEventIds(List<UUID> eventIds) {
        return jpaQueryFactory
                .selectFrom(sp)
                .join(sp.user, user).fetchJoin()
                .where(sp.event.eventId.in(eventIds))
                .fetch();
    }
}
