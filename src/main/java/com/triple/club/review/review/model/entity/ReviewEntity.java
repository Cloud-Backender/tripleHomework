package com.triple.club.review.review.model.entity;

import com.triple.club.common.model.entitiy.TimeEntity;
import com.triple.club.review.place.model.entity.PlaceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "REVIEW")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewEntity extends TimeEntity {
    @Id
    @Column(name = "REVIEW_ID")
    private String reviewId;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "ATTACHED_PHOTO_IDS")
    private String attachedPhotoIds;

    @Column(name = "USER_ID")
    private String userId;

    @ManyToOne(targetEntity = PlaceEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PLACE_ID", referencedColumnName = "PLACE_ID")
    private PlaceEntity place;

    public void updateContent(String content) {
        this.content = content;
    }

    public void updatePhotos(String photos) {
        this.attachedPhotoIds = photos;
    }

    public PlaceEntity updatePlace(PlaceEntity placeEntity) {
        return placeEntity;
    }
}
