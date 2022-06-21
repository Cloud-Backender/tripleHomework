package com.triple.club.review.service.impl;

import com.google.gson.Gson;
import com.triple.club.review.model.contant.ActionType;
import com.triple.club.review.model.contant.EventType;
import com.triple.club.review.model.dto.ReviewDto;
import com.triple.club.review.repository.PointLogRepo;
import com.triple.club.review.repository.ReviewRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {
    @InjectMocks
    ReviewServiceImpl service;
    @Mock
    ReviewRepo reviewRepo;
    @Mock
    PointLogRepo pointLogRepo;

    @Test
    void reviewEvent() {

    }

    @Test
    void addEvent() {
    }

    @Test
    void modEvent() {
    }

    @Test
    void deleteEvent() {
    }

    @Test
    void addPoint() {
    }

    @Test
    void modReview() {
    }

    @Test
    void getTotalPoint() {
    }
}
