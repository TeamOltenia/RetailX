package com.fmi.demo.domain.model.enums;

import lombok.Getter;

@Getter
public enum WheatherEnum {

    SUNNY("Sunny"),
    COLD("Cold"),
    WINDY("Windy"),
    CLOUDY("Cloudy"),
    RAINY("Rainy");

    private String name;

    WheatherEnum(String name){
        this.name =name;
    }
}

