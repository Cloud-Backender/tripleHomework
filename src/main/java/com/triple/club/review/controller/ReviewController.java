package com.triple.club.review.controller;

import com.triple.club.review.model.contant.ReviewConstant;
import com.triple.club.review.model.dto.ReviewDto;
import com.triple.club.review.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    private final EventService eventService;


    @Autowired
    public ReviewController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/events")
    public ResponseEntity<String> reviewEvent(@RequestBody ReviewDto review) throws Exception {
        if(!review.getType().equals(ReviewConstant.REVIEW) || review.getType().isEmpty()) {
            return new ResponseEntity<>("잘못된 요청",HttpStatus.BAD_REQUEST);
        }
        eventService.reviewEvent(review);
        return new ResponseEntity<>("성공",HttpStatus.OK);
    }
}
