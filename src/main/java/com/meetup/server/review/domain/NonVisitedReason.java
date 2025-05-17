package com.meetup.server.review.domain;

import com.meetup.server.review.domain.type.NonVisitedReasonCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "non_visited_reason")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class NonVisitedReason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "non_visited_reason_id")
    private Long nonVisitedReasonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = true)
    private NonVisitedReasonCategory category;

    @Builder
    public NonVisitedReason(Review review, NonVisitedReasonCategory category) {
        this.review = review;
        this.category = category;
    }
}
