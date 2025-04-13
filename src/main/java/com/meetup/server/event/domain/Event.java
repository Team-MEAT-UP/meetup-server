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
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Event extends BaseEntity {

    @Id
    @Column(name = "event_id")
    private UUID eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subway_id", nullable = false)
    private Subway subway;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_id", nullable = false)
    private Recommend recommend;

    @PrePersist
    public void prePersist() {
        eventId = UUID.randomUUID();
    }

    @Builder
    public Event(Subway subway, Member member, Recommend recommend) {
        this.subway = subway;
        this.member = member;
        this.recommend = recommend;
    }
}
