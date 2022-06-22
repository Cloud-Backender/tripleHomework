package com.triple.club.review.service.impl;

import com.triple.club.point.repository.PointLogRepo;
import com.triple.club.review.repository.ReviewRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
