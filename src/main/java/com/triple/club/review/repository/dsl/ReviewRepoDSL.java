package com.triple.club.review.repository.dsl;


public interface ReviewRepoDSL {
    boolean existMyReviewInPlace(String placeId, String userId);
    boolean notExistReviewInPlace(String placeId);
}
