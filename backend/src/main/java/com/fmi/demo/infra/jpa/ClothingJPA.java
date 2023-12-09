package com.fmi.demo.infra.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "clothing")
@Entity(name="Clothing")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClothingJPA extends BasicEntityJPA{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

//    @Column(name="colors")
//    private String colors;

    @Column(name="availability")
    private boolean availability;

    @Column(name="size")
    private String size;

    @Column(name="clothes_category")
    private String clothesCategory;

    @ManyToOne
    @JoinColumn(name="clothes_brand")
    ClothesBrandJPA clothesBrand;

}
