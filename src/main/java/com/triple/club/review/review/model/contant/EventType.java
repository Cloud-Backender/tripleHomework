package com.triple.club.review.review.model.contant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EventType {
    ERROR("ERROR"),
    REVIEW("REVIEW");
    private final String value;

    @JsonCreator
    public static EventType creator(String value) {
        return Arrays.stream(values()).filter(type -> type.getValue().equals(value)).findAny().orElse(ERROR);
    }

    }
