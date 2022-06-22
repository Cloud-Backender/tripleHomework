package com.triple.club.review.service;

import com.google.gson.Gson;
import com.triple.club.common.exception.CustomException;
import com.triple.club.point.service.PointService;
import com.triple.club.review.model.contant.ActionType;
import com.triple.club.review.model.contant.EventType;
import com.triple.club.review.model.dto.ReviewDto;
import com.triple.club.point.repository.PointLogRepo;
import com.triple.club.review.repository.ReviewRepo;
import com.triple.club.review.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class ReviewServiceTest {
    @Autowired
    private EntityManager em;

    private final ReviewRepo reviewRepo;
    private final PointLogRepo pointLogRepository;

    @Autowired
    public ReviewServiceTest(EntityManager em, ReviewRepo reviewRepo, PointLogRepo pointLogRepository) {
        this.em = em;
        this.reviewRepo = reviewRepo;
        this.pointLogRepository = pointLogRepository;
    }



    @InjectMocks
    private ReviewServiceImpl service;
    private PointService pointService;


    @BeforeEach
    void setUp() {
        this.service = new ReviewServiceImpl(this.reviewRepo, this.pointLogRepository, pointService, em);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void reviewEvent() throws CustomException {
        ReviewDto reviewDto = ReviewDto.builder()
                .type(EventType.REVIEW)
                .attachedPhotoIds(new String[]{"test", "test2"})
                .reviewId("testReview")
                .placeId("place1")
                .userId("jjuser")
                .content("this is test")
                .action(ActionType.ADD)
                .build();

        String json = new Gson().toJson(reviewDto);
        service.reviewEvent(reviewDto);
    }

    @Test
    void getTotalPoint() {
    }
}
