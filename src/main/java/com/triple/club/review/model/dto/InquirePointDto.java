package com.triple.club.review.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InquirePointDto {
    private String userId;
    private Long totalPoint;
}
