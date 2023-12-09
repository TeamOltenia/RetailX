package com.fmi.demo.domain.model;

import com.fmi.demo.infra.jpa.BasicEntityJPA;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClothesBrand {

    private String id;
    private String name;
    private String homepage;

}
