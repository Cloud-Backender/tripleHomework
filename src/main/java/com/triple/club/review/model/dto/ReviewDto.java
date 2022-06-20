package com.triple.club.review.model.dto;

import com.triple.club.review.model.contant.ActionType;
import com.triple.club.review.model.contant.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    EventType type;
    ActionType action;
    String reviewId;
    String content;
    String[] attachedPhotoIds;
    String userId;
    String placeId;

}
