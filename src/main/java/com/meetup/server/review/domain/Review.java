package com.meetup.server.review.domain;

import com.meetup.server.recommend.domain.RecommendPlace;
import com.meetup.server.review.domain.type.VisitedTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_place_id", nullable = false)
    private RecommendPlace recommendPlace;

    @Column(name = "is_visited", nullable = false)
    private boolean isVisited;

    @Enumerated(EnumType.STRING)
    @Column(name = "visited_time", nullable = false)
    private VisitedTime visitedTime;

    @Column(name = "review_content", length = 255, nullable = true)
    private String content;

    @Column(name = "etc_reason", length = 255, nullable = true)
    private String etcReason;

    @Builder
    public Review(RecommendPlace recommendPlace, boolean isVisited, VisitedTime visitedTime, String content, String etcReason) {
        this.recommendPlace = recommendPlace;
        this.isVisited = isVisited;
        this.visitedTime = visitedTime;
        this.content = content;
        this.etcReason = etcReason;
    }
}
