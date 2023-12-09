package com.fmi.demo.infra.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "ClothesBrand")
@Table(name = "clothes_brand")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClothesBrandJPA extends BasicEntityJPA {

    @Column(name="name")
    private String name;

    @Column(name="homepage")
    private String homepage;

    @OneToMany(mappedBy = "clothesBrand")
    Set<ClothingJPA> clothings = new HashSet<>();
}
