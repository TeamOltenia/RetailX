package com.fmi.demo.domain.model;

import com.fmi.demo.domain.model.enums.OccasionsEnum;
import com.fmi.demo.domain.model.enums.SeasonsEnum;
import com.fmi.demo.domain.model.enums.WheatherEnum;
import com.fmi.demo.infra.jpa.BasicEntityJPA;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OutfitSuggestions {


    private String id;
    private String name;
    private List<String> clothesIds;
    private OccasionsEnum occasion;
    private SeasonsEnum season;
    private WheatherEnum wheather;
}
