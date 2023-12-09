package com.fmi.demo.domain.model;

import com.fmi.demo.domain.model.enums.OccasionsEnum;
import com.fmi.demo.domain.model.enums.SeasonsEnum;
import com.fmi.demo.infra.jpa.BasicEntityJPA;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavedOutfits  {

    private String id;
    private String name;
    private List<String> clothesIds = new ArrayList<>();

    private List<Clothing> clothes = new ArrayList<>();
    private OccasionsEnum occasion;
    private SeasonsEnum season;
    private String userId;

}
