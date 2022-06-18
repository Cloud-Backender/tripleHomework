package com.triple.club.review.controller;

import com.triple.club.common.exception.ApiExceptionCode;
import com.triple.club.common.exception.CustomException;
import com.triple.club.review.model.contant.ReviewConstant;
import com.triple.club.review.model.dto.ReviewDto;
import com.triple.club.review.model.entity.ReviewEntity;
import com.triple.club.review.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * fileName       : ReviewController
 * author         : kimjaejung
 * createDate     : 2022/06/16
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/06/16        kimjaejung       최초 생성
 */
@RestController
public class ReviewController {
    private final Logger logger = LogManager.getLogger(ReviewController.class);
    private final ReviewService reviewService;


    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/events")
    public ResponseEntity<ReviewEntity> reviewEvent(@RequestBody ReviewDto review) throws CustomException {
        if(!review.getType().equals(ReviewConstant.REVIEW) || review.getType().isEmpty()) {
            throw new CustomException(ApiExceptionCode.SYSTEM_ERROR);
        }
        ReviewEntity reviewEntity = reviewService.reviewEvent(review);
        return new ResponseEntity<>(reviewEntity,HttpStatus.OK);
    }
    @GetMapping("/total-point/{userId}")
    public ResponseEntity<Long> getTotalPoint(@PathVariable String userId) throws CustomException {
        long result = reviewService.getTotalPoint(userId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
