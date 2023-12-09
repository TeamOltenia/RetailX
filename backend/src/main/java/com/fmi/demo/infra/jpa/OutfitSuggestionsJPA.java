package com.fmi.demo.infra.jpa;

import com.fmi.demo.domain.model.enums.OccasionsEnum;
import com.fmi.demo.domain.model.enums.SeasonsEnum;
import com.fmi.demo.domain.model.enums.WheatherEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "outfit_suggestions")
@Entity(name="OutfitSuggestions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OutfitSuggestionsJPA extends BasicEntityJPA{

    @Column(name="name")
    private String name;

    @Column(name="clothes_ids")
    private String clothesIds;

    @Column(name="user_id" , nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "occasion", nullable = false)
    private OccasionsEnum occasion;

    @Enumerated(EnumType.STRING)
    @Column(name = "season", nullable = false)
    private SeasonsEnum season;

    @Enumerated(EnumType.STRING)
    @Column(name = "wheather", nullable = false)
    private WheatherEnum wheather;
}
