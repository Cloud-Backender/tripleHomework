package com.triple.club.review.service;

import com.triple.club.common.exception.CustomException;
import com.triple.club.point.service.PointService;
import com.triple.club.review.model.contant.ActionType;
import com.triple.club.review.model.contant.EventType;
import com.triple.club.review.model.dto.ReviewDto;
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

    @Autowired
    public ReviewServiceTest(EntityManager em, ReviewRepo reviewRepo, PointService pointService) {
        this.em = em;
        this.reviewRepo = reviewRepo;
        this.pointService = pointService;
    }



    @InjectMocks
    private ReviewServiceImpl service;
    private PointService pointService;


    @BeforeEach
    void setUp() {
        this.service = new ReviewServiceImpl(this.reviewRepo, pointService, em);

    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    @DisplayName("리뷰 서비스 테스트")
    class 리뷰테스트 {
        @Test
        @DisplayName("리뷰 생성")
        void createReview() throws CustomException {
            ReviewDto newReview = ReviewDto.builder()
                    .type(EventType.REVIEW)
                    .attachedPhotoIds(new String[]{"test", "test2"})
                    .reviewId("testReview")
                    .placeId("place1")
                    .userId("jjuser")
                    .content("this is test")
                    .action(ActionType.ADD)
                    .build();
            service.reviewEvent(newReview);

        }

        @Test
        @DisplayName("리뷰 수정")
        void modReview() throws CustomException {
            createReview();
            ReviewDto modReivew = ReviewDto.builder()
                    .type(EventType.REVIEW)
                    .attachedPhotoIds(new String[]{"test-mod", "test2-mod"})
                    .reviewId("testReview")
                    .placeId("place1")
                    .userId("jjuser")
                    .content("this is modified")
                    .action(ActionType.MOD)
                    .build();
            service.reviewEvent(modReivew);
        }
        @Test
        @DisplayName("리뷰 삭제")
        void deleteReview() throws CustomException {
            createReview();
            ReviewDto deleteReview = ReviewDto.builder()
                    .type(EventType.REVIEW)
                    .attachedPhotoIds(new String[]{"test-mod", "test2-mod"})
                    .reviewId("testReview")
                    .placeId("place1")
                    .userId("jjuser")
                    .content("this is modified")
                    .action(ActionType.DELETE)
                    .build();
            service.reviewEvent(deleteReview);
        }
    }

}
