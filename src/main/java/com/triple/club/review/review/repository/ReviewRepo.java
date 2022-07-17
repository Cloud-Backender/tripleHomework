package com.triple.club.review.review.repository;

import com.triple.club.review.review.model.entity.ReviewEntity;
//import com.triple.club.review.review.repository.dsl.ReviewRepoDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepo extends JpaRepository<ReviewEntity, Long> {
//public interface ReviewRepo extends JpaRepository<ReviewEntity, Long>, ReviewRepoDSL {
    Optional<ReviewEntity> findByReviewId(String reviewId);
//    Optional<ReviewEntity> findTop1ByPlaceIdOrderByCreateTimeAsc(String placeId);
}
