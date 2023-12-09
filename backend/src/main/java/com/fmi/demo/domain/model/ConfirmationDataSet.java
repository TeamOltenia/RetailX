package com.fmi.demo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationDataSet {

    private String id;
    private String confirmedUserId;
    private String addedUserId;
    private String imageUrl;
    private Boolean confirmed;
    private String category;

    private String image;
}
