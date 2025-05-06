package com.meetup.server.subway.domain;

import com.meetup.server.global.domain.BaseEntity;
import com.meetup.server.startpoint.domain.type.Location;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subway")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Subway extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subway_id")
    private Integer subwayId;

    @Column(name = "subway_name", length = 50, nullable = false)
    private String name;

    @Column(name = "subway_code", nullable = false)
    private int code;

    @Column(name = "subway_line", nullable = false)
    private int line;

    @Embedded
    private Location location;

    @Builder
    public Subway(String name, int code, int line, Location location) {
        this.name = name;
        this.code = code;
        this.line = line;
        this.location = location;
    }
}
