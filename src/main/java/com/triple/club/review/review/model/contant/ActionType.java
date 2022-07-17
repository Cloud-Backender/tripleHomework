package com.triple.club.review.review.model.contant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum ActionType {
    ERROR("ERROR"),
    ADD("ADD"),
    MOD("MOD"),
    DELETE("DELETE");
    private final String value;

    @JsonCreator
    public static ActionType creator(String value) {
        return Arrays.stream(values()).filter(type -> type.getValue().equals(value)).findAny().orElse(ERROR);
    }

}
