package com.fmi.demo.infra.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "confirmation_dataset")
@Entity(name="ConfirmationDataset")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationDataSetJPA extends BasicEntityJPA{

    @Column(name = "confirmed_by_user")
    private String confirmedUserId;

    @Column(name = "added_by_user")
    private String addedUserId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "confirmed")
    private Boolean confirmed;

    @Column(name = "clothes_category")
    private String category;
}
