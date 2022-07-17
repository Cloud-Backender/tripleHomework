package com.triple.club.point.repository;

import com.triple.club.point.model.entity.PointLogEntity;
//import com.triple.club.review.review.repository.dsl.ReviewRepoDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointLogRepo extends JpaRepository<PointLogEntity, Long> {
//public interface PointLogRepo extends JpaRepository<PointLogEntity, Long>, ReviewRepoDSL {
    Optional<PointLogEntity> findTop1ByUserIdOrderBySeqDesc(String userId);
}
