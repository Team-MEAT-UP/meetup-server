package com.meetup.server.event.domain;


import com.meetup.server.global.domain.BaseEntity;
import com.meetup.server.member.domain.Member;
import com.meetup.server.recommend.domain.Recommend;
import com.meetup.server.subway.domain.Subway;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "event")
@NoArgsConstructor
@Getter
public class Event extends BaseEntity {

    @Id
    @Column(name = "event_id")
    private UUID eventId;

    @ManyToOne
    @JoinColumn(name = "subway_id", nullable = false)
    private Subway subwayId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member memberId;

    @ManyToOne
    @JoinColumn(name = "recommend_id", nullable = false)
    private Recommend recommendId;

    @PrePersist
    public void prePersist() {
        eventId = UUID.randomUUID();
    }

    @Builder
    public Event(Subway subwayId, Member memberId, Recommend recommendId) {
        this.subwayId = subwayId;
        this.memberId = memberId;
        this.recommendId = recommendId;
    }
}
