package com.fmi.demo.infra.jpa.mapper;

import com.fmi.demo.domain.model.ConfirmationDataSet;
import com.fmi.demo.infra.jpa.ConfirmationDataSetJPA;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {
        }
)
public interface ConfirmationDataSetJPAMapper extends GenericMapper<ConfirmationDataSet, ConfirmationDataSetJPA> {

}
