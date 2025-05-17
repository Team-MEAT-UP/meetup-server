package com.meetup.server.event.domain;


import com.github.f4b6a3.uuid.UuidCreator;
import com.meetup.server.global.domain.BaseEntity;
import com.meetup.server.recommend.domain.RecommendPlace;
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
    @JoinColumn(name = "subway_id", nullable = true)
    private Subway subway;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_id", nullable = true)
    private RecommendPlace recommendPlace;

    @Column(name = "is_reviwed", nullable = false)
    private boolean isReviewed;

    @PrePersist
    public void prePersist() {

        this.eventId = UuidCreator.getTimeOrderedEpoch();
    }

    @Builder
    public Event(Subway subway, RecommendPlace recommendPlace) {
        this.subway = subway;
        this.recommendPlace = recommendPlace;
    }

    public void updateSubway(Subway subway) {
        this.subway = subway;
    }
}
