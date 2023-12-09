package com.fmi.demo.infra.jpa.mapper;

import com.fmi.demo.domain.model.ClothesBrand;
import com.fmi.demo.infra.jpa.ClothesBrandJPA;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {}
        )
public interface ClothesBrandJPAMapper extends GenericMapper<ClothesBrand, ClothesBrandJPA> {
}
