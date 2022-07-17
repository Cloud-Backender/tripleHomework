package com.triple.club.review.review.service;

import com.triple.club.common.exception.CustomException;
import com.triple.club.review.review.model.dto.ReviewDto;

public interface ReviewService {
    ReviewDto reviewEvent(ReviewDto event) throws CustomException;
}
