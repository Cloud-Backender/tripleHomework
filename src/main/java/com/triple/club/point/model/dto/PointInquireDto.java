package com.triple.club.point.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointInquireDto {
    private String userId;
    private Long totalPoint;
}
