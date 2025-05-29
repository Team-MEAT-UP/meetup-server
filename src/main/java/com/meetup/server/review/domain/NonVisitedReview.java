package com.meetup.server.review.domain;

import com.meetup.server.global.domain.BaseEntity;
import com.meetup.server.review.domain.type.NonVisitedReasonCategory;
import com.meetup.server.review.domain.value.ActualVisitedPlace;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "non_visited_review")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class NonVisitedReview extends BaseEntity {

    @Id
    @Column(name = "review_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "review_id")
    private Review review;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    @Enumerated(EnumType.STRING)
    private List<NonVisitedReasonCategory> categories = new ArrayList<>();

    @Column(name = "etc_reason")
    private String etcReason;

    @Embedded
    private ActualVisitedPlace actualVisitedPlace;

    @Builder
    public NonVisitedReview(Review review, List<NonVisitedReasonCategory> categories, String etcReason, ActualVisitedPlace actualVisitedPlace) {
        this.review = review;
        this.categories = categories;
        this.etcReason = etcReason;
        this.actualVisitedPlace = actualVisitedPlace;
    }

    public void assignTo(Review review) {
        this.review = review;
    }
}
