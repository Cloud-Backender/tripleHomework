package com.triple.club.review.place.controller;

import com.triple.club.common.exception.CustomException;
import com.triple.club.common.model.dto.ResponseObject;
import com.triple.club.review.place.model.dto.PlaceDto;
import com.triple.club.review.place.model.entity.PlaceEntity;
import com.triple.club.review.place.service.PlaceService;
import com.triple.club.review.review.model.entity.ReviewEntity;
import com.triple.club.review.review.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceController {
    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping("/place")
    public ResponseEntity<ResponseObject> reviewEvent(@RequestBody PlaceDto placeDto) throws CustomException {
        ResponseObject responseObject = new ResponseObject();
        placeService.createPlace(placeDto);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }



}
