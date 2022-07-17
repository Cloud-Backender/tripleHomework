package com.triple.club.review.place.repository;

import com.triple.club.review.place.model.entity.PlaceEntity;
import com.triple.club.review.place.repository.dsl.PlaceRepoDSL;
import com.triple.club.review.review.model.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepo extends JpaRepository<PlaceEntity, String>, PlaceRepoDSL {
}
