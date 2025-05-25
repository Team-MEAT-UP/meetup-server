package com.meetup.server.user.persistence;

import com.meetup.server.user.domain.QUser;
import com.meetup.server.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meetup.server.review.domain.QNonVisitedReview.nonVisitedReview;
import static com.meetup.server.review.domain.QReview.review;
import static com.meetup.server.review.domain.QVisitedReview.visitedReview;
import static com.meetup.server.startpoint.domain.QStartPoint.startPoint;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteUser(User user) {
        List<Long> reviewIds = jpaQueryFactory.select(review.id)
                .from(review)
                .where(review.user.userId.eq(user.getUserId()))
                .fetch();

        if (!reviewIds.isEmpty()) {
            jpaQueryFactory.delete(nonVisitedReview)
                    .where(nonVisitedReview.review.id.in(reviewIds))
                    .execute();

            jpaQueryFactory.delete(visitedReview)
                    .where(visitedReview.review.id.in(reviewIds))
                    .execute();

            jpaQueryFactory.delete(review)
                    .where(review.user.userId.eq(user.getUserId()))
                    .execute();
        }

        jpaQueryFactory.delete(startPoint)
                .where(startPoint.user.userId.eq(user.getUserId()))
                .execute();

        jpaQueryFactory.delete(QUser.user)
                .where(QUser.user.userId.eq(user.getUserId()))
                .execute();
    }
}
