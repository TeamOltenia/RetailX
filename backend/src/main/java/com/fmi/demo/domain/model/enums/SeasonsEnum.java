package com.fmi.demo.domain.model.enums;

import lombok.Getter;

@Getter
public enum SeasonsEnum {

    WINTER("Winter"),
    SPIRNG("Spring"),
    SUMMER("Summer"),
    FALL("Fall");

    private String name;

    SeasonsEnum(String name){
        this.name =name;
    }
}
