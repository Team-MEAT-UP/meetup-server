package com.meetup.server.recommend.domain;

import com.meetup.server.event.domain.Event;
import com.meetup.server.recommend.domain.type.RecommendCategory;
import com.meetup.server.startpoint.domain.type.Location;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recommend_place")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class RecommendPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long recommendId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private RecommendCategory category;

    @Column(name = "place_name", length = 255, nullable = false)
    private String placeName;

    @Embedded
    private Location location;

    @Builder
    public RecommendPlace(Location location, String placeName, Event event, RecommendCategory category) {
        this.location = location;
        this.placeName = placeName;
        this.event = event;
        this.category = category;
    }
}
