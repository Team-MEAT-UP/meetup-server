package com.meetup.server.event.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import com.meetup.server.global.domain.BaseEntity;
import com.meetup.server.place.domain.Place;
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
    @JoinColumn(name = "place_id", nullable = true)
    private Place place;

    @PrePersist
    public void prePersist() {
        this.eventId = UuidCreator.getTimeOrderedEpoch();
    }

    @Builder
    public Event(Subway subway, Place place) {
        this.subway = subway;
        this.place = place;
    }

    public void updateSubway(Subway subway) {
        this.subway = subway;
    }

    public void confirmPlace(Place place) {
        this.place = place;
    }

    public void updatePlace(Place place) {
        if (this.place.getId().equals(place.getId())) {
            return;
        }
        this.place = place;
    }

    public boolean hasNoPlace() {
        return this.place == null;
    }
}
