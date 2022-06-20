package com.triple.club.review.repository;

import com.triple.club.review.model.entity.PointLogEntity;
import com.triple.club.review.repository.dsl.ReviewRepoDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointLogRepo extends JpaRepository<PointLogEntity, Long>, ReviewRepoDSL {
    Optional<PointLogEntity> findTop1ByUserIdOrderBySeqDesc(String userId);
}
