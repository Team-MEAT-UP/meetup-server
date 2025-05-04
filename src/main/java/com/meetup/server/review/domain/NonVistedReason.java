package com.meetup.server.review.domain;

import com.meetup.server.review.domain.type.NonVistedReasonCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "non_visited_reason")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class NonVistedReason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "non_visited_reason_id")
    private Long nonVisitedReasonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = true)
    private NonVistedReasonCategory category;

    @Builder
    public NonVistedReason(Review review, NonVistedReasonCategory category) {
        this.review = review;
        this.category = category;
    }
}
