package com.triple.club.review.service;

import com.triple.club.common.exception.CustomException;
import com.triple.club.review.model.dto.InquirePointDto;
import com.triple.club.review.model.dto.ReviewDto;
import com.triple.club.review.model.entity.ReviewEntity;

public interface ReviewService {
    ReviewDto reviewEvent(ReviewDto event) throws CustomException;
    InquirePointDto getTotalPoint(String userId) throws CustomException;
}
