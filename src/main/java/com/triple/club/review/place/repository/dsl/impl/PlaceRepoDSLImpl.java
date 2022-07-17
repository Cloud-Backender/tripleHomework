package com.triple.club.review.place.repository.dsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triple.club.common.exception.ApiExceptionCode;
import com.triple.club.common.exception.CustomException;
import com.triple.club.review.place.model.entity.PlaceEntity;
import com.triple.club.review.place.model.entity.QPlaceEntity;
import com.triple.club.review.place.repository.dsl.PlaceRepoDSL;
import com.triple.club.review.review.model.entity.QReviewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlaceRepoDSLImpl implements PlaceRepoDSL {
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public PlaceRepoDSLImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public boolean existMyReviewInPlace(String placeId, String userId) {
        QReviewEntity reviewEntity = new QReviewEntity("review");

        boolean isExist = jpaQueryFactory.selectOne()
                .from(reviewEntity)
                .where(reviewEntity.place.placeId.eq(placeId).and(reviewEntity.userId.eq(userId)))
                .fetchFirst() != null;
        return isExist;
    }

    @Override
    public boolean existReviewInPlace(String placeId) {
        QReviewEntity reviewEntity = new QReviewEntity("review");

        boolean isNotExist = jpaQueryFactory.selectOne()
                .from(reviewEntity)
                .where(reviewEntity.place.placeId.eq(placeId))
                .fetchFirst() == null;
        return isNotExist;

    }

    @Override
    public boolean existPlace(String placeId) {
        QPlaceEntity placeEntity = new QPlaceEntity("place");

        boolean isExist = jpaQueryFactory.selectOne()
                .from(placeEntity)
                .where(placeEntity.placeId.eq(placeId))
                .fetchFirst() == null;
        return isExist;
    }

    @Override
    public Optional<PlaceEntity> findByPlaceIdReviewId(String placeId, String reviewId)  {
        QPlaceEntity placeEntity = new QPlaceEntity("place");
        Optional<PlaceEntity> result = Optional.ofNullable(jpaQueryFactory
                .select(placeEntity)
                .from(placeEntity)
                .where(placeEntity.placeId.eq(placeId).and(placeEntity.reviews.any().reviewId.eq(reviewId)))
                .fetchOne());
        return result;
    }
}
