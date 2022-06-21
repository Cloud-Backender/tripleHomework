package com.triple.club.review.repository.dsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triple.club.review.model.entity.QReviewEntity;
import com.triple.club.review.repository.dsl.ReviewRepoDSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ReviewRepoDSLImpl implements ReviewRepoDSL {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public ReviewRepoDSLImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public boolean existMyReviewInPlace(String placeId, String userId) {
        QReviewEntity reviewEntity = new QReviewEntity("review");

        boolean isExist  = jpaQueryFactory.selectOne()
                .from(reviewEntity)
                .where(reviewEntity.placeId.eq(placeId).and(reviewEntity.userId.eq(userId)))
                .fetchFirst() != null;
        return isExist;
    }

    @Override
    public boolean existReviewInPlace(String placeId) {
        QReviewEntity reviewEntity = new QReviewEntity("review");

        boolean isExist  = jpaQueryFactory.selectOne()
                .from(reviewEntity)
                .where(reviewEntity.placeId.eq(placeId))
                .fetchFirst() != null;
        return isExist;
    }
}
