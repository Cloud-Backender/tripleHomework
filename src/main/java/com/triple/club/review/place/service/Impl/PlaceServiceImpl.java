package com.triple.club.review.place.service.Impl;

import com.triple.club.review.place.model.dto.PlaceDto;
import com.triple.club.review.place.model.entity.PlaceEntity;
import com.triple.club.review.place.repository.PlaceRepo;
import com.triple.club.review.place.service.PlaceService;
import com.triple.club.review.review.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepo placeRepo;

    @Autowired
    public PlaceServiceImpl(PlaceRepo placeRepo) {
        this.placeRepo = placeRepo;
    }

    @Override
    public void createPlace(PlaceDto placeDto) {
        PlaceEntity placeEntity = PlaceEntity.builder()
                .placeName(placeDto.getPlaceName())
                .placeId(placeDto.getPlaceId())
                .placeCategory(placeDto.getPlaceCategory()).build();

        placeRepo.save(placeEntity);
    }
}
