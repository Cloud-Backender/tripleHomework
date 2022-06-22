package com.triple.club.point.service.impl;

import com.triple.club.common.exception.ApiExceptionCode;
import com.triple.club.common.exception.CustomException;
import com.triple.club.point.model.dto.InquirePointDto;
import com.triple.club.point.model.entity.PointLogEntity;
import com.triple.club.point.repository.PointLogRepo;
import com.triple.club.point.service.PointService;
import com.triple.club.review.model.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

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
        Optional<PointLogEntity> presentEntity = pointLogRepository.findTop1ByUserIdOrderBySeqDesc(reviewDto.getUserId());
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

    }
    @Override
    public void removePoint(String userId, String reason) {
        Optional<PointLogEntity> presentEntity = pointLogRepository.findTop1ByUserIdOrderBySeqDesc(userId);
        PointLogEntity pointLogEntity;

        if (presentEntity.isEmpty()) {
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
                    .reviewId(presentEntity.get().getReviewId())
                    .totalPoint(presentEntity.get().getTotalPoint() > 0 ? presentEntity.get().getTotalPoint() - 1 : 0)
                    .build();
        }
        em.persist(pointLogEntity);
    }

    @Override
    public InquirePointDto getTotalPoint(String userId) throws CustomException {
        Optional<PointLogEntity> pointLogEntity = pointLogRepository.findTop1ByUserIdOrderBySeqDesc(userId);
        if (pointLogEntity.isPresent()){
            InquirePointDto result = InquirePointDto.builder()
                    .userId(userId)
                    .totalPoint(pointLogEntity.get().getTotalPoint())
                    .build();
            return result;
        } else {
            throw new CustomException(ApiExceptionCode.NOT_EXIST_USER);
        }
    }
}
