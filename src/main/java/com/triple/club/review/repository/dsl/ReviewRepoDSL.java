package com.triple.club.review.repository.dsl;

public interface ReviewRepoDSL {
    boolean existReviewInPlace(String placeId, String userId);
}
