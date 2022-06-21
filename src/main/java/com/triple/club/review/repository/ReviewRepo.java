package com.triple.club.review.repository;

import com.triple.club.review.model.entity.ReviewEntity;
import com.triple.club.review.repository.dsl.ReviewRepoDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepo extends JpaRepository<ReviewEntity, Long>, ReviewRepoDSL {
    boolean existsByPlaceIdAndUserId(String placeId, String userId);
    boolean existsByPlaceId(String placeId);
    Optional<ReviewEntity> findByReviewIdAndUserId(String reviewId, String userId);
    Optional<ReviewEntity> findTop1ByPlaceIdOrderByCreateTime(String placeId);
    Optional<ReviewEntity> findByReviewId(String reviewId);
    Optional<ReviewEntity> findTop1ByPlaceId(String placeId);
}
