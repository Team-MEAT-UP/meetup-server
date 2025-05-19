package com.meetup.server.review.persistence;

import com.meetup.server.review.domain.VisitedReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitedReviewRepository extends JpaRepository<VisitedReview, Long> {
}
