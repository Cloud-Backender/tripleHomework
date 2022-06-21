package com.triple.club.review.repository;

import com.triple.club.common.configuration.BeanConfig;
import com.triple.club.review.model.entity.ReviewEntity;
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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepoTest {

    @Autowired
    private ReviewRepo reviewRepo;


    @Test
    void findByReviewIdAndUserId() {
        ReviewEntity reviewEntity = ReviewEntity.builder()
                .placeId("PLACE123")
                .reviewId("REVIEW123")
                .attachedPhotoIds("test,test1")
                .userId("JJUSER123")
                .content("content test")
                .build();

        ReviewEntity savedReview = reviewRepo.save(reviewEntity);
        assertEquals(reviewEntity.getReviewId(), savedReview.getReviewId());

        Optional<ReviewEntity> findedReview = reviewRepo.findByReviewIdAndUserId(savedReview.getReviewId(), savedReview.getUserId());

        assertTrue(findedReview.isPresent());
    }

    @Test
    void findTop1ByPlaceIdOrderByCreateTime() {
    }

    @Test
    void findByPlaceIdAndUserId() {
    }
}
