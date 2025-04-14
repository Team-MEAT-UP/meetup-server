package com.meetup.server.recommend.domain;

import jakarta.persistence.*;
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
}
