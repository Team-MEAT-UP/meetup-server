package com.meetup.server.startpoint.domain;

import com.meetup.server.event.domain.Event;
import com.meetup.server.global.domain.BaseEntity;
import com.meetup.server.startpoint.domain.type.Address;
import com.meetup.server.startpoint.domain.type.Location;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "start_point")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Startpoint extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "start_point_id")
    private Long startPointId;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event eventId;

    @Embedded
    private Address address;

    @Embedded
    private Location location;

    @Builder
    public Startpoint(Event eventId, Address address, Location location) {
        this.eventId = eventId;
        this.address = address;
        this.location = location;
    }
}
