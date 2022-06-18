package com.triple.club.review.service.impl;

import com.triple.club.review.model.dto.ReviewDto;
import com.triple.club.review.model.entity.ReviewEntity;
import com.triple.club.review.model.entity.PointLogEntity;
import com.triple.club.review.repository.ReviewRepository;
import com.triple.club.review.repository.PointLogRepository;
import com.triple.club.review.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final PointLogRepository pointLogRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PointLogRepository pointLogRepository) {
        this.reviewRepository = reviewRepository;
        this.pointLogRepository = pointLogRepository;
    }

    @Override
    public void reviewEvent(ReviewDto event) throws Exception {
        switch (event.getAction()) {
            case ADD:
                addEvent(event);
                break;
            case MOD:
                modEvent(event);
                break;
            case DELETE:
                deleteEvent(event);
                break;
            default:

                break;
        }
    }

    public void addEvent(ReviewDto event) throws Exception {
        if (!reviewRepository.existsByPlaceIdAndUserId(event.getPlaceId(), event.getUserId())) {
            addPoint(event, "장소의 첫 리뷰 +1 Point.");
        } else {
            throw new Exception("해당 장소에 이미 리뷰를 작성함");
        }

        ReviewEntity reviewEntity = ReviewEntity.builder()
                .reviewId(event.getReviewId())
                .content(event.getContent())
                .attachedPhotoIds((event.getAttachedPhotoIds().length == 0 || event.getAttachedPhotoIds() == null)?
                        "" : Arrays.stream(event.getAttachedPhotoIds()).map(String::toString).collect(Collectors.joining(","))
                )
                .userId(event.getUserId())
                .placeId(event.getPlaceId())
                .build();
        em.persist(reviewEntity);
//        reviewRepository.save(reviewEntity);


        if (event.getContent().length() > 0) {
            addPoint(event, "리뷰 내용 작성 +1 Point.");
        }

        if (event.getAttachedPhotoIds().length > 0) {
            addPoint(event, "리뷰 사진 작성 +1 Point.");
        }
    }
    public void modEvent(ReviewDto eventReview) throws Exception {
        Optional<ReviewEntity> reviewEntity = reviewRepository.findByReviewId(eventReview.getReviewId());
        if (reviewEntity.isPresent()) {
            compareReview(eventReview, reviewEntity.get());
        } else {
            throw new Exception("수정할 리뷰가 없음.");
        }

    }
    public void deleteEvent(ReviewDto eventReview) throws Exception {
        Optional<ReviewEntity> reviewEntity = reviewRepository.findByReviewId(eventReview.getReviewId());
        if (reviewEntity.isPresent()) {
            compareReview(eventReview, reviewEntity.get());
        } else {
            throw new Exception("삭제할 리뷰가 없음.");
        }
    }

    void addPoint(ReviewDto reviewDto, String reason) {
        Optional<PointLogEntity> presentEntity = pointLogRepository.findTopByUserIdOrderBySeqDesc(reviewDto.getUserId());
        PointLogEntity pointLogEntity;

        if (presentEntity.isEmpty()) {
            pointLogEntity = PointLogEntity.builder()
                    .userId(reviewDto.getUserId())
                    .reason(reason)
                    .totalPoint(1L)
                    .reviewId(reviewDto.getReviewId())
                    .build();
        } else {
            pointLogEntity = PointLogEntity.builder()
                    .userId(reviewDto.getUserId())
                    .reason(reason)
                    .totalPoint(presentEntity.get().getTotalPoint() + 1)
                    .reviewId(reviewDto.getReviewId())
                    .build();
        }
        em.persist(pointLogEntity);
//        pointLogRepository.save(pointLogEntity);

    }
    private void removePoint(String userId, String reason) {
        Optional<PointLogEntity> presentEntity = pointLogRepository.findTopByUserIdOrderBySeqDesc(userId);
        PointLogEntity pointLogEntity;

        if (presentEntity.isEmpty()) {
            pointLogEntity = PointLogEntity.builder()
                    .userId(userId)
                    .reason(reason)
                    .totalPoint(0L)
                    .build();
        } else {
            pointLogEntity = PointLogEntity.builder()
                    .userId(userId)
                    .reason(reason)
                    .totalPoint(presentEntity.get().getTotalPoint() > 0 ? presentEntity.get().getTotalPoint() - 1 : 0)
                    .build();
        }
        pointLogRepository.save(pointLogEntity);
    }

    private void compareReview(ReviewDto eventReview, ReviewEntity preReview) {
        if (eventReview.getContent().length() > 0 && !(preReview.getContent().length() > 0)) {
            addPoint(eventReview, "기존 리뷰 내용 작성 +1 Point.");
        } else if (!(eventReview.getContent().length() > 0) && preReview.getContent().length() > 0) {
            removePoint(eventReview.getUserId(), "기존 리뷰 내용 삭제 -1 Point.");
        }
        preReview.updateContent(eventReview.getContent());


        if (eventReview.getAttachedPhotoIds().length > 0 && !(preReview.getAttachedPhotoIds().length() > 0)) {
            addPoint(eventReview, "기존 리뷰 사진 작성 +1 Point.");
        } else if (!(eventReview.getAttachedPhotoIds().length >0) && preReview.getAttachedPhotoIds().length() > 0) {
            removePoint(eventReview.getUserId(), "기존 리뷰 사진 삭제 -1 Point.");
        }

        preReview.updatePhotos(eventReview.getAttachedPhotoIds().length > 0 ?
                Arrays.stream(eventReview.getAttachedPhotoIds()).map(String::toString).collect(Collectors.joining(",")) : ""
                );

        reviewRepository.save(preReview);

    }
}
