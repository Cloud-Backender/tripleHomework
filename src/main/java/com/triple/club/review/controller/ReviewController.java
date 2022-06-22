package com.triple.club.review.controller;

import com.triple.club.common.exception.ApiExceptionCode;
import com.triple.club.common.exception.CustomException;
import com.triple.club.common.model.dto.ResponseObject;
import com.triple.club.review.model.contant.ActionType;
import com.triple.club.review.model.contant.EventType;
import com.triple.club.point.model.dto.InquirePointDto;
import com.triple.club.review.model.dto.ReviewDto;
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
    public ResponseEntity<ResponseObject> reviewEvent(@RequestBody ReviewDto review) throws CustomException {
        ResponseObject responseObject = new ResponseObject();
        if(review.getType().equals(EventType.ERROR)) {
            throw new CustomException(ApiExceptionCode.NOT_EXIST_EVENT_TYPE);
        }
        if(review.getAction().equals(ActionType.ERROR)) {
            throw new CustomException(ApiExceptionCode.NOT_EXIST_EVENT_ACTION_TYPE);
        }

        ReviewDto result = reviewService.reviewEvent(review);
        responseObject.setBody(result);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("test",HttpStatus.OK);
    }
}
