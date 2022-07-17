package com.triple.club.review.place.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private String placeId;
    private String placeName;
    private String placeCategory;

}
