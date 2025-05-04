package com.meetup.server.review.domain;

import com.meetup.server.review.domain.type.ScoreCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "satisfaction")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Satisfaction {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long satisfactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(name = "category", nullable = false)
    private ScoreCategory category;

    @Column(name = "score", nullable = false)
    private int score;

    @Builder
    public Satisfaction(Review review, ScoreCategory category, int score) {
        this.review = review;
        this.category = category;
        this.score = score;
    }
}
