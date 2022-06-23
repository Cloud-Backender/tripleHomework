package com.triple.club.point.service;

import com.triple.club.common.exception.CustomException;
import com.triple.club.point.repository.PointLogRepo;
import com.triple.club.point.service.impl.PointServiceImpl;
import com.triple.club.review.model.dto.ReviewDto;
import com.triple.club.review.model.entity.ReviewEntity;
import com.triple.club.review.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PointServiceTest {

    @Autowired
    private EntityManager em;

    private final PointLogRepo pointLogRepo;

    @Autowired
    public PointServiceTest(EntityManager em, PointLogRepo pointLogRepo) {
        this.em = em;
        this.pointLogRepo = pointLogRepo;
    }

    @InjectMocks
    private PointServiceImpl service;


    @BeforeEach
    void setUp() {
        this.service = new PointServiceImpl(pointLogRepo, em);
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    @DisplayName("포인트_서비스_테스트")
    class 포인트_서비스_테스트 {

        @Test
        void addPoint() {
            ReviewDto reviewEntity = ReviewDto.builder()
                    .placeId("testPlace")
                    .reviewId("testReview")
                    .attachedPhotoIds(new String[]{"abc"})
                    .userId("testUser")
                    .content("this is test")
                    .build();

            service.addPoint(reviewEntity, "+1 test Point");
        }

        @Test
        void removePoint() {
            addPoint();
            service.removePoint("testUser", "+1 test Point");
        }

        @Test
        void getTotalPoint() throws CustomException {
            addPoint();
            service.getTotalPoint("testUser");
        }
    }
}
