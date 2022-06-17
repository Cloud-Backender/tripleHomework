package com.triple.club.review.service.impl;

import com.triple.club.review.model.dto.ReviewDto;
import com.triple.club.review.model.entity.ReviewEntity;
import com.triple.club.review.model.entity.PointLogEntity;
import com.triple.club.review.repository.ReviewRepository;
import com.triple.club.review.repository.PointLogRepository;
import com.triple.club.review.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final ReviewRepository reviewRepository;
    private final PointLogRepository pointLogRepository;

    @Autowired
    public EventServiceImpl(ReviewRepository reviewRepository, PointLogRepository pointLogRepository) {
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addEvent(ReviewDto event) throws Exception {
        if (!reviewRepository.existsByPlaceId(event.getPlaceId())) {
            addPoint(event.getUserId(), "장소의 첫 리뷰 +1 Point.");
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
        reviewRepository.save(reviewEntity);


        if (event.getContent().length() > 0) {
            addPoint(event.getUserId(), "리뷰 내용 작성 +1 Point.");
        }

        if (event.getAttachedPhotoIds().length > 0) {
            addPoint(event.getUserId(), "리뷰 사진 작성 +1 Point.");
        }

    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void modEvent(ReviewDto eventReview) throws Exception {
        Optional<ReviewEntity> reviewEntity = reviewRepository.findByReviewId(eventReview.getReviewId());
        if (reviewEntity.isPresent()) {
            compareReview(eventReview, reviewEntity.get());
        } else {
            throw new Exception("수정할 리뷰가 없음.");
        }

    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteEvent(ReviewDto eventReview) throws Exception {
        Optional<ReviewEntity> reviewEntity = reviewRepository.findByReviewId(eventReview.getReviewId());
        if (reviewEntity.isPresent()) {
            compareReview(eventReview, reviewEntity.get());
        } else {
            throw new Exception("삭제할 리뷰가 없음.");
        }
    }

    private void addPoint(String userId, String reason) {
        Optional<PointLogEntity> presentEntity = pointLogRepository.findTopByUserIdOrderByCreateTimeDesc(userId);
        PointLogEntity pointLogEntity;

        if (presentEntity.isEmpty()) {
            pointLogEntity = PointLogEntity.builder()
                    .userId(userId)
                    .reason(reason)
                    .totalPoint(1L)
                    .build();
        } else {
            pointLogEntity = PointLogEntity.builder()
                    .userId(userId)
                    .reason(reason)
                    .totalPoint(presentEntity.get().getTotalPoint() + 1)
                    .build();
        }
        pointLogRepository.save(pointLogEntity);
    }
    private void removePoint(String userId, String reason) {
        Optional<PointLogEntity> presentEntity = pointLogRepository.findTopByUserIdOrderByCreateTimeDesc(userId);
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
                    .reason("")
                    .totalPoint(presentEntity.get().getTotalPoint() > 0 ? presentEntity.get().getTotalPoint() - 1 : 0)
                    .build();
        }
        pointLogRepository.save(pointLogEntity);
    }

    private void compareReview(ReviewDto eventReview, ReviewEntity preReview) {
        if (eventReview.getContent().length() > 0 && !(preReview.getContent().length() > 0)) {
            addPoint(eventReview.getUserId(), "기존 리뷰 내용 작성 +1 Point.");
        } else if (!(eventReview.getContent().length() > 0) && preReview.getContent().length() > 0) {
            removePoint(eventReview.getUserId(), "기존 리뷰 내용 삭제 -1 Point.");
        }
        preReview.updateContent(eventReview.getContent());


        if (eventReview.getAttachedPhotoIds().length > 0 && !(preReview.getAttachedPhotoIds().length() > 0)) {
            addPoint(eventReview.getUserId(), "기존 리뷰 사진 작성 +1 Point.");
        } else if (!(eventReview.getAttachedPhotoIds().length >0) && preReview.getAttachedPhotoIds().length() > 0) {
            removePoint(eventReview.getUserId(), "기존 리뷰 사진 삭제 -1 Point.");
        }

        preReview.updatePhotos(eventReview.getAttachedPhotoIds().length > 0 ?
                Arrays.stream(eventReview.getAttachedPhotoIds()).map(String::toString).collect(Collectors.joining(",")) : ""
                );

        reviewRepository.save(preReview);

    }
}
