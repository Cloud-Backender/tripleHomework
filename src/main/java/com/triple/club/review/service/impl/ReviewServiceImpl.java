package com.triple.club.review.service.impl;

import com.triple.club.common.exception.ApiExceptionCode;
import com.triple.club.common.exception.CustomException;
import com.triple.club.point.service.PointService;
import com.triple.club.review.model.dto.ReviewDto;
import com.triple.club.review.model.entity.ReviewEntity;
import com.triple.club.review.repository.ReviewRepo;
import com.triple.club.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepo reviewRepo;
    private final PointService pointService;
    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public ReviewServiceImpl(ReviewRepo reviewRepo, PointService pointService, EntityManager em) {
        this.reviewRepo = reviewRepo;
        this.pointService = pointService;
        this.em = em;
    }

    @Override
    public ReviewDto reviewEvent(ReviewDto event) throws CustomException {
        ReviewEntity reviewEntity;
        switch (event.getAction()) {
            case ADD:
                reviewEntity = addEvent(event);
                break;
            case MOD:
                reviewEntity = modEvent(event);
                break;
            case DELETE:
                deleteEvent(event);
                return null;
            default:
                throw new CustomException(ApiExceptionCode.SYSTEM_ERROR);
        }
        ReviewDto reviewDto = ReviewDto.builder()
                .userId(reviewEntity.getUserId())
                .placeId(reviewEntity.getPlaceId())
                .reviewId(reviewEntity.getReviewId())
                .content(reviewEntity.getContent())
                .attachedPhotoIds(reviewEntity.getAttachedPhotoIds().split(","))
                .build();
        return reviewDto;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ReviewEntity addEvent(ReviewDto event) throws CustomException {
        if (reviewRepo.existMyReviewInPlace(event.getPlaceId(), event.getUserId())) {
            throw new CustomException(ApiExceptionCode.ALREADY_EXIST_REVIEW);
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

        if (reviewRepo.notExistReviewInPlace(event.getPlaceId())) {
            pointService.addPoint(event, "+1 Point : 장소의 첫 리뷰");
        }
        if (event.getContent().length() > 0) {
            pointService.addPoint(event, "+1 Point : 리뷰 내용 작성");
        }

        if (event.getAttachedPhotoIds().length > 0) {
            pointService.addPoint(event, "+1 Point 리뷰 사진 작성");
        }

        em.persist(reviewEntity);

        return reviewEntity;
    }

    public ReviewEntity modEvent(ReviewDto eventReview) throws CustomException {
        Optional<ReviewEntity> reviewEntity = reviewRepo.findByReviewId(eventReview.getReviewId());
        if (reviewEntity.isPresent()) {
            return modReview(eventReview, reviewEntity.get());
        } else {
            throw new CustomException(ApiExceptionCode.NOT_EXIST_REVIEW);
        }
    }
    public void deleteEvent(ReviewDto eventReview) throws CustomException {
        Optional<ReviewEntity> reviewEntity = reviewRepo.findByReviewId(eventReview.getReviewId());
        if (reviewEntity.isPresent()) {
            deleteReview(reviewEntity.get());
        } else {
            throw new CustomException(ApiExceptionCode.NOT_EXIST_REVIEW);
        }
        em.remove(reviewEntity.get());
    }

    public void deleteReview(ReviewEntity reviewEntity) {
        Optional<ReviewEntity> firstReview = reviewRepo.findTop1ByPlaceId(reviewEntity.getPlaceId());
        firstReview.ifPresent(data ->  {
                if(data.getReviewId().equals(reviewEntity.getReviewId())){
                    pointService.removePoint(reviewEntity.getUserId(), "-1 Point : 장소의 첫 리뷰 포인트 회수");
                }
        });

        Optional<ReviewEntity> receivedReview = reviewRepo.findByReviewId(reviewEntity.getReviewId());

        receivedReview.ifPresent(review -> {
            if (review.getContent().length() > 0) {
                pointService.removePoint(review.getUserId(), "-1 Point : 리뷰 삭제 포인트 회수(내용)");
            }
            if (review.getAttachedPhotoIds().length() > 0) {
                pointService.removePoint(review.getUserId(), "-1 Point : 리뷰 삭제 포인트 회수(사진)");
            }
        });
    }



    ReviewEntity modReview(ReviewDto eventReview, ReviewEntity preReview) {
        if (eventReview.getContent().length() > 0 && !(preReview.getContent().length() > 0)) {
            pointService.addPoint(eventReview, "+1 Point : 기존 리뷰 내용 작성");
        } else if (!(eventReview.getContent().length() > 0) && preReview.getContent().length() > 0) {
            pointService.removePoint(eventReview.getUserId(), "-1 Point : 기존 리뷰 내용 삭제");
        }
        preReview.updateContent(eventReview.getContent());


        if (eventReview.getAttachedPhotoIds().length > 0 && !(preReview.getAttachedPhotoIds().length() > 0)) {
            pointService.addPoint(eventReview, "+1 Point : 기존 리뷰 사진 작성");
        } else if (!(eventReview.getAttachedPhotoIds().length >0) && preReview.getAttachedPhotoIds().length() > 0) {
            pointService.removePoint(eventReview.getUserId(), "-1 Point : 기존 리뷰 사진 삭제");
        }

        preReview.updatePhotos(eventReview.getAttachedPhotoIds().length > 0 ?
                Arrays.stream(eventReview.getAttachedPhotoIds()).map(String::toString).collect(Collectors.joining(",")) : ""
                );

        em.persist(preReview);
        return preReview;

    }
}
