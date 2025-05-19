package com.meetup.server.review.persistence;

import com.meetup.server.review.domain.NonVisitedReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NonVisitedReviewRepository extends JpaRepository<NonVisitedReview, Long> {
}
