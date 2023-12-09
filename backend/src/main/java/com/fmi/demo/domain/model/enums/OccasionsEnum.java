package com.fmi.demo.domain.model.enums;

import lombok.Getter;

@Getter
public enum OccasionsEnum {

    FORMAL("Formal"),
    CASUAL("Casual");
    private String name;

    OccasionsEnum(String name){
        this.name =name;
    }
}
