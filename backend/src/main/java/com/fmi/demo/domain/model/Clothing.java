package com.fmi.demo.domain.model;

import lombok.*;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Clothing  {

    protected String id;
    private String name;
    private String description;
    private String image;
//    private List<String> colors = new ArrayList<>();
    private String userId;
    private boolean availability;
    private String size;
    private String imageUrl;
    private String clothesCategory;
    ClothesBrand clothesBrand;

}
