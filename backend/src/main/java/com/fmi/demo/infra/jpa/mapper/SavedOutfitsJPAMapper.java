package com.fmi.demo.infra.jpa.mapper;

import com.fmi.demo.domain.model.OutfitSuggestions;
import com.fmi.demo.domain.model.SavedOutfits;
import com.fmi.demo.infra.jpa.OutfitSuggestionsJPA;
import com.fmi.demo.infra.jpa.SavedOutfitsJPA;
import com.fmi.demo.infra.jpa.mapper.custom.CutomeMapperUtil;
import com.fmi.demo.infra.jpa.mapper.custom.ListToString;
import com.fmi.demo.infra.jpa.mapper.custom.StringToList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                CutomeMapperUtil.class
        }
)
public interface SavedOutfitsJPAMapper extends GenericMapper<SavedOutfits, SavedOutfitsJPA> {
    @Override
    @Mapping(source = "clothesIds" , target = "clothesIds" , qualifiedBy = StringToList.class)
    SavedOutfits toDomain(SavedOutfitsJPA savedOutfitsJPA);

    @Override
    @Mapping(source = "clothesIds" , target = "clothesIds" , qualifiedBy = ListToString.class)
    SavedOutfitsJPA toDto(SavedOutfits savedOutfits);
}
