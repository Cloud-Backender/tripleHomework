package com.triple.club.point.service;

import com.triple.club.common.exception.CustomException;
import com.triple.club.point.model.dto.InquirePointDto;
import com.triple.club.review.model.dto.ReviewDto;

public interface PointService {
    void addPoint(ReviewDto reviewDto, String reason);

    void removePoint(String userId, String reason);

    InquirePointDto getTotalPoint(String userId) throws CustomException;
}
