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
public class StartPoint extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "start_point_id")
    private Long startPointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "start_point_name", nullable = false)
    private String name;

    @Embedded
    private Address address;

    @Embedded
    private Location location;

    @Builder
    public StartPoint(Event event, String name, Address address, Location location) {
        this.event = event;
        this.name = name;
        this.address = address;
        this.location = location;
    }
}
