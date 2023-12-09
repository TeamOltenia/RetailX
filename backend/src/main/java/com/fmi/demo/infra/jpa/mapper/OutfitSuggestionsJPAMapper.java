package com.fmi.demo.infra.jpa.mapper;

import com.fmi.demo.domain.model.OutfitSuggestions;
import com.fmi.demo.infra.jpa.OutfitSuggestionsJPA;
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
public interface OutfitSuggestionsJPAMapper extends GenericMapper<OutfitSuggestions, OutfitSuggestionsJPA> {

        @Override
        @Mapping(source = "clothesIds" , target = "clothesIds" , qualifiedBy = StringToList.class)
        OutfitSuggestions toDomain(OutfitSuggestionsJPA outfitSuggestionsJPA);

        @Override
        @Mapping(source = "clothesIds" , target = "clothesIds" , qualifiedBy = ListToString.class)
        OutfitSuggestionsJPA toDto(OutfitSuggestions outfitSuggestions);

}
