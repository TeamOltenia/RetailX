package com.fmi.demo.infra.repository;

import com.fmi.demo.domain.model.OutfitSuggestions;
import com.fmi.demo.domain.repository.OutfitSuggestionsRepository;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import com.fmi.demo.infra.jpa.OutfitSuggestionsJPA;
import com.fmi.demo.infra.jpa.mapper.OutfitSuggestionsJPAMapper;
import com.fmi.demo.infra.repository.jpa.OutfitSuggestionsJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
@RequiredArgsConstructor
@Repository
public class OutfitSuggestionsRepositoryImpl implements OutfitSuggestionsRepository {

    private OutfitSuggestionsJPAMapper outfitSuggestionsJPAMapper;

    private OutfitSuggestionsJPARepository outfitSuggestionsJPARepository;

    @Override
    public String save(OutfitSuggestions clothing){
        return outfitSuggestionsJPARepository.save(outfitSuggestionsJPAMapper.toDto(clothing)).getId();
    }

    @Override
    public void delete(String id) {
        outfitSuggestionsJPARepository.deleteById(id);

    }

    @Override
    public Optional<OutfitSuggestions> getById(String id) {
        Optional<OutfitSuggestionsJPA> outfitSuggestionsJPA = outfitSuggestionsJPARepository.findById(id);
        if(outfitSuggestionsJPA.isEmpty()){
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        return Optional.of(outfitSuggestionsJPAMapper.toDomain(outfitSuggestionsJPA.get()));
    }

    @Override
    public boolean existsById(String id) {
        return outfitSuggestionsJPARepository.existsById(id);
    }

    @Override
    public List<OutfitSuggestions> getAll() {
        return outfitSuggestionsJPARepository.findAll().stream().map(outfitSuggestionsJPAMapper::toDomain).toList();
    }
}
