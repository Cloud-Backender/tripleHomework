package com.triple.club.point.controller;

import com.triple.club.common.exception.CustomException;
import com.triple.club.common.model.dto.ResponseObject;
import com.triple.club.point.model.dto.InquirePointDto;
import com.triple.club.point.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PointController {
    private final PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("/total-point/{userId}")
    public ResponseEntity<ResponseObject> getTotalPoint(@PathVariable String userId) throws CustomException {
        ResponseObject responseObject = new ResponseObject();
        InquirePointDto result = pointService.getTotalPoint(userId);
        responseObject.setBody(result);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

}
