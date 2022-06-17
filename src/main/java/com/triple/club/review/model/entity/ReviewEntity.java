package com.triple.club.review.model.entity;

import com.triple.club.common.entitiy.TimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.stream.Collectors;

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

    @Column(name = "PLACE_ID")
    private String placeId;

    public void updateContent(String content) {
        this.content = content;
    }

    public void updatePhotos(String photos) {
        this.attachedPhotoIds = photos;
    }
}
