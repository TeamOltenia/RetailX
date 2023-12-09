package com.fmi.demo.infra.jpa.mapper;

import com.fmi.demo.domain.model.Clothing;
import com.fmi.demo.domain.model.OutfitSuggestions;
import com.fmi.demo.infra.jpa.ClothingJPA;
import com.fmi.demo.infra.jpa.OutfitSuggestionsJPA;
import com.fmi.demo.infra.jpa.mapper.custom.CutomeMapperUtil;
import com.fmi.demo.infra.jpa.mapper.custom.ListToString;
import com.fmi.demo.infra.jpa.mapper.custom.StringToList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                ClothesBrandJPAMapper.class,
                CutomeMapperUtil.class
        }
)
public interface ClothingJPAMapper extends GenericMapper<Clothing, ClothingJPA> {

        @Override
//        @Mapping(source = "colors" , target = "colors" , qualifiedBy = StringToList.class)
        Clothing toDomain(ClothingJPA clothingJPA);

        @Override
//        @Mapping(source = "colors" , target = "colors" , qualifiedBy = ListToString.class)
        ClothingJPA toDto(Clothing clothing);

}
