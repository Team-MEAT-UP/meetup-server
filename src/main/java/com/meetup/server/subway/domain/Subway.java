package com.meetup.server.subway.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subway")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Subway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subway_id")
    private Long subwayId;
}
