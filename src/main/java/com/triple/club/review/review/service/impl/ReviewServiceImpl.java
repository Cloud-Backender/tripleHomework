package com.triple.club.review.review.service.impl;

import com.triple.club.common.exception.ApiExceptionCode;
import com.triple.club.common.exception.CustomException;
import com.triple.club.point.service.PointService;
import com.triple.club.review.place.model.entity.PlaceEntity;
import com.triple.club.review.place.repository.PlaceRepo;
import com.triple.club.review.review.model.dto.ReviewDto;
import com.triple.club.review.review.model.entity.ReviewEntity;
import com.triple.club.review.review.repository.ReviewRepo;
import com.triple.club.review.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepo reviewRepo;
    private final PlaceRepo placeRepo;
    private final PointService pointService;
    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public ReviewServiceImpl(ReviewRepo reviewRepo, PlaceRepo placeRepo, PointService pointService, EntityManager em) {
        this.reviewRepo = reviewRepo;
        this.placeRepo = placeRepo;
        this.pointService = pointService;
        this.em = em;
    }

    @Override
    public ReviewDto reviewEvent(ReviewDto event) throws CustomException {
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
                throw new CustomException(ApiExceptionCode.SYSTEM_ERROR);
        }
        return event;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addEvent(ReviewDto event) throws CustomException {
        if (placeRepo.existPlace(event.getPlaceId())) {
            throw new CustomException(ApiExceptionCode.NOT_EXIST_PLACE);
        }

        if (placeRepo.existMyReviewInPlace(event.getPlaceId(), event.getUserId())) {
            throw new CustomException(ApiExceptionCode.ALREADY_EXIST_REVIEW);
        }


        Optional<PlaceEntity> placeEntity = placeRepo.findById(event.getPlaceId());
        placeEntity.ifPresent(place -> {
            ReviewEntity reviewEntity = ReviewEntity.builder()
                    .reviewId(event.getReviewId())
                    .content(event.getContent())
                    .attachedPhotoIds((event.getAttachedPhotoIds().length == 0 || event.getAttachedPhotoIds() == null) ?
                            "" : Arrays.stream(event.getAttachedPhotoIds()).map(String::toString).collect(Collectors.joining(","))
                    )
                    .place(place)
                    .userId(event.getUserId())
                    .build();
            try {
                place.addReview(reviewEntity);
            } catch (CustomException e) {
                e.printStackTrace();
            }
        });

        if (!placeRepo.existReviewInPlace(event.getPlaceId())) {
            pointService.addPoint(event, "+1 Point : 장소의 첫 리뷰");
        }
        if (event.getContent().length() > 0) {
            pointService.addPoint(event, "+1 Point : 리뷰 내용 작성");
        }

        if (event.getAttachedPhotoIds().length > 0) {
            pointService.addPoint(event, "+1 Point : 리뷰 사진 작성");
        }

        em.persist(placeEntity.get());
    }

    public void modEvent(ReviewDto eventReview) {
        Optional<PlaceEntity> preEntity = placeRepo.findByPlaceIdReviewId(eventReview.getPlaceId(), eventReview.getReviewId());
        preEntity.ifPresent(data -> modReview(eventReview, data));
    }

    public void deleteEvent(ReviewDto eventReview) {
        Optional<PlaceEntity> placeEntity = placeRepo.findByPlaceIdReviewId(eventReview.getPlaceId(), eventReview.getReviewId());
        placeEntity.ifPresent(this::deleteReview);

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteReview(PlaceEntity deleteEntity) {
        Optional<PlaceEntity> placeEntity = placeRepo.findByPlaceIdReviewId(deleteEntity.getPlaceId(), deleteEntity.getReviews().get(0).getReviewId());
        placeEntity.ifPresent(data -> {
            if (data.getReviews().get(0).getReviewId().equals(deleteEntity.getReviews().get(0).getReviewId())) {
                pointService.removePoint(deleteEntity.getReviews().get(0).getUserId(), "-1 Point : 장소의 첫 리뷰 포인트 회수");
            }
            if (data.getReviews().get(0).getContent().length() > 0) {
                pointService.removePoint(data.getReviews().get(0).getUserId(), "-1 Point : 리뷰 삭제 포인트 회수(내용)");
            }
            if (data.getReviews().get(0).getAttachedPhotoIds().length() > 0) {
                pointService.removePoint(data.getReviews().get(0).getUserId(), "-1 Point : 리뷰 삭제 포인트 회수(사진)");
            }
            data.getReviews().remove(0);
            em.persist(data);
        });
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    void modReview(ReviewDto eventReview, PlaceEntity preReview) {
        if (eventReview.getContent().length() > 0 && !(preReview.getReviews().get(0).getContent().length() > 0)) {
            pointService.addPoint(eventReview, "+1 Point : 기존 리뷰 내용 작성");
        } else if (!(eventReview.getContent().length() > 0) && preReview.getReviews().get(0).getContent().length() > 0) {
            pointService.removePoint(eventReview.getUserId(), "-1 Point : 기존 리뷰 내용 삭제");
        }
        preReview.getReviews().get(0).updateContent(eventReview.getContent());


        if (eventReview.getAttachedPhotoIds().length > 0 && !(preReview.getReviews().get(0).getAttachedPhotoIds().length() > 0)) {
            pointService.addPoint(eventReview, "+1 Point : 기존 리뷰 사진 작성");
        } else if (!(eventReview.getAttachedPhotoIds().length > 0) && preReview.getReviews().get(0).getAttachedPhotoIds().length() > 0) {
            pointService.removePoint(eventReview.getUserId(), "-1 Point : 기존 리뷰 사진 삭제");
        }

        preReview.getReviews().get(0).updatePhotos(eventReview.getAttachedPhotoIds().length > 0 ?
                Arrays.stream(eventReview.getAttachedPhotoIds()).map(String::toString).collect(Collectors.joining(",")) : ""
        );

        em.persist(preReview);
    }
}
