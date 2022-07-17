package com.triple.club.review.place.repository.dsl;

import com.triple.club.common.exception.CustomException;
import com.triple.club.review.place.model.entity.PlaceEntity;

import java.util.Optional;

public interface PlaceRepoDSL {
    boolean existMyReviewInPlace(String placeId, String userId);
    boolean existReviewInPlace(String placeId);
    boolean existPlace(String placeId);
    Optional<PlaceEntity> findByPlaceIdReviewId(String placeId, String reviewId) ;

}
