package com.triple.club.review.service;

import com.triple.club.review.model.dto.ReviewDto;

public interface ReviewService {
    void reviewEvent(ReviewDto event) throws Exception;

}
