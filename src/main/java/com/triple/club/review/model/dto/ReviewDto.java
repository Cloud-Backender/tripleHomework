package com.triple.club.review.model.dto;

import com.triple.club.review.model.contant.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * fileName       : reviewDto
 * author         : kimjaejung
 * createDate     : 2022/06/16
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/06/16        kimjaejung       최초 생성
 *
 */
@AllArgsConstructor
@Getter
@Builder
public class ReviewDto {
    String type;
    EventType action;
    String reviewId;
    String content;
    String[] attachedPhotoIds;
    String userId;
    String placeId;
}
