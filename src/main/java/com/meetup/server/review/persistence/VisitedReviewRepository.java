package com.meetup.server.review.persistence;

import com.meetup.server.review.domain.VisitedReview;
import com.meetup.server.review.persistence.projection.PlaceWithRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface VisitedReviewRepository extends JpaRepository<VisitedReview, Long> {

    @Query("""
                SELECT new com.meetup.server.review.persistence.projection.PlaceWithRating(
                    vr.review.place,
                    AVG(vr.placeScore.socket),
                    AVG(vr.placeScore.seat),
                    AVG(vr.placeScore.quiet)
                )
                FROM VisitedReview vr
                WHERE vr.review.place.id IN :placeIds
                GROUP BY vr.review.place
            """)
    List<PlaceWithRating> findPlaceRatingsByPlaceIds(@Param("placeIds") List<UUID> placeIds);
}
