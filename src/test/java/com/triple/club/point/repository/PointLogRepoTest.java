package com.triple.club.point.repository;

import com.triple.club.common.configuration.BeanConfig;
import com.triple.club.review.review.model.entity.ReviewEntity;
import com.triple.club.review.review.repository.ReviewRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(BeanConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointLogRepoTest {

    private PointLogRepo pointLogRepo;
    private ReviewRepo reviewRepo;

    @Autowired
    public PointLogRepoTest(PointLogRepo pointLogRepo, ReviewRepo reviewRepo) {
        this.pointLogRepo = pointLogRepo;
        this.reviewRepo = reviewRepo;
    }



    @Nested
    @DisplayName("포인트_레포_테스트")
    class 포인트_레포_테스트 {

        @DisplayName("ReviewId로 검색")
        @Test
        void findTop1ByUserIdOrderBySeqDesc() {
            ReviewEntity reviewEntity = ReviewEntity.builder()
//                    .placeId("PLACE123")
                    .reviewId("REVIEW123")
                    .attachedPhotoIds("test,test1")
                    .userId("JJUSER123")
                    .content("content test")
                    .build();
            reviewRepo.save(reviewEntity);

            pointLogRepo.findTop1ByUserIdOrderBySeqDesc("JJUSER123");

        }
    }
}
