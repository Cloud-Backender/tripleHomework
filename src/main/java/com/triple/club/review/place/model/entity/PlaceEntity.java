package com.triple.club.review.place.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.triple.club.common.exception.ApiExceptionCode;
import com.triple.club.common.exception.CustomException;
import com.triple.club.common.model.entitiy.TimeEntity;
import com.triple.club.review.review.model.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PLACE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlaceEntity extends TimeEntity {
    @Id
    @Column(name = "PLACE_ID")
    private String placeId;

    @Column(name = "PLACE_NAME")
    private String placeName;

    @Column(name = "PLACE_CATEGORY")
    private String placeCategory;

    @OneToMany(mappedBy = "place", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ReviewEntity> reviews;

    public void addReview(ReviewEntity reviewEntity) throws CustomException {
        if(reviewEntity == null) {
            throw new CustomException(ApiExceptionCode.NOT_EXIST_REVIEW);
        }
        this.reviews.add(reviewEntity);
    }
}
