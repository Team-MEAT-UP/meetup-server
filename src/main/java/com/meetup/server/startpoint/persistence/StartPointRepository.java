package com.meetup.server.startpoint.persistence;

import com.meetup.server.event.domain.Event;
import com.meetup.server.startpoint.domain.StartPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface StartPointRepository extends JpaRepository<StartPoint, UUID> {

    int countByEvent(Event event);

    @Query("SELECT DISTINCT s FROM StartPoint s JOIN FETCH s.event WHERE s.event = :event")
    List<StartPoint> findAllByEvent(@Param("event") Event event);

    @Query("""
                SELECT s
                FROM StartPoint s
                WHERE s.event.eventId = :eventId
                ORDER BY s.createdAt ASC
                LIMIT 1
            """)
    StartPoint findTopByEventIdOrderByCreatedAtAsc(@Param("eventId") UUID eventId);

    @Query("""
            SELECT sp
            FROM StartPoint sp
            JOIN FETCH sp.user
            WHERE sp.user.userId = :userId""")
    List<StartPoint> findAllByUserId(@Param("userId") Long userId);
}
