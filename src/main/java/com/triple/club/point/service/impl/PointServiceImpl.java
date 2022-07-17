package com.triple.club.point.service.impl;

import com.triple.club.common.exception.ApiExceptionCode;
import com.triple.club.common.exception.CustomException;
import com.triple.club.point.model.dto.PointInquireDto;
import com.triple.club.point.model.entity.PointLogEntity;
import com.triple.club.point.repository.PointLogRepo;
import com.triple.club.point.service.PointService;
import com.triple.club.review.review.model.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class PointServiceImpl implements PointService {
    private final PointLogRepo pointLogRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public PointServiceImpl(PointLogRepo pointLogRepository, EntityManager em) {
        this.pointLogRepository = pointLogRepository;
        this.em = em;
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void addPoint(ReviewDto reviewDto, String reason) {
        Optional<PointLogEntity> pointEntity = pointLogRepository.findTop1ByUserIdOrderBySeqDesc(reviewDto.getUserId());
        PointLogEntity pointLogEntity;

        if (pointEntity.isEmpty()) {
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
                    .totalPoint(pointEntity.get().getTotalPoint() + 1)
                    .reviewId(reviewDto.getReviewId())
                    .build();
        }
        em.persist(pointLogEntity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void removePoint(String userId, String reason) {
        Optional<PointLogEntity> pointEntity = pointLogRepository.findTop1ByUserIdOrderBySeqDesc(userId);
        PointLogEntity pointLogEntity;

        if (pointEntity.isEmpty()) {
            pointLogEntity = PointLogEntity.builder()
                    .userId(userId)
                    .reviewId("0 Point 초기화")
                    .reason(reason)
                    .totalPoint(0L)
                    .build();
        } else {
            pointLogEntity = PointLogEntity.builder()
                    .userId(userId)
                    .reason(reason)
                    .reviewId(pointEntity.get().getReviewId())
                    .totalPoint(pointEntity.get().getTotalPoint() > 0 ?
                            pointEntity.get().getTotalPoint() - 1 : 0)
                    .build();
        }
        em.persist(pointLogEntity);
    }

    @Override
    public PointInquireDto getTotalPoint(String userId) throws CustomException {
        Optional<PointLogEntity> pointEntity = pointLogRepository.findTop1ByUserIdOrderBySeqDesc(userId);
        PointInquireDto result;
        if (pointEntity.isPresent()){
            result = PointInquireDto.builder()
                    .userId(userId)
                    .totalPoint(pointEntity.get().getTotalPoint())
                    .build();
            return result;
        } else {
            throw new CustomException(ApiExceptionCode.NOT_EXIST_USER);
        }
    }
}
