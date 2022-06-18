package com.triple.club.review.service;

import com.triple.club.common.exception.CustomException;
import com.triple.club.review.model.dto.ReviewDto;
import com.triple.club.review.model.entity.ReviewEntity;

public interface ReviewService {
    ReviewEntity reviewEvent(ReviewDto event) throws CustomException;
    long getTotalPoint(String userId) throws CustomException;
}
