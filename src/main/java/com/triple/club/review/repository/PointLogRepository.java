package com.triple.club.review.repository;

import com.triple.club.review.model.entity.PointLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointLogRepository extends JpaRepository<PointLogEntity, Long> {
    Optional<PointLogEntity> findTopByUserIdOrderBySeq(String userId);
}
