package com.triple.club.review.repository;

import com.triple.club.common.configuration.BeanConfig;
import com.triple.club.review.model.entity.ReviewEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(BeanConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepoTest {

    private ReviewRepo reviewRepo;

    @Autowired
    public ReviewRepoTest(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Nested
    @DisplayName("리뷰 레포 테스트")
    class 리뷰_레포_테스트 {

        @DisplayName("ReviewId로 검색")
        @Test
        void findByReviewId() {
            ReviewEntity reviewEntity = ReviewEntity.builder()
                    .placeId("PLACE123")
                    .reviewId("REVIEW123")
                    .attachedPhotoIds("test,test1")
                    .userId("JJUSER123")
                    .content("content test")
                    .build();

            ReviewEntity savedReview = reviewRepo.save(reviewEntity);
            assertEquals(reviewEntity.getReviewId(), savedReview.getReviewId());

            Optional<ReviewEntity> findedReview = reviewRepo.findByReviewId(savedReview.getReviewId());

            assertTrue(findedReview.isPresent());
        }

        @Test
        @DisplayName("삭제 시 보너스 포인트 유무")
        void findTop1ByPlaceId() {
                findByReviewId();

                assertTrue(reviewRepo.findTop1ByPlaceId("PLACE123").isPresent());
        }

    }
}
