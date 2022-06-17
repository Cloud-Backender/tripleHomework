package com.triple.club.review.repository;

import com.triple.club.review.model.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    boolean existsByPlaceIdAndUserId(String placeId, String userId);
    Optional<ReviewEntity> findByReviewId(String reviewId);
}
