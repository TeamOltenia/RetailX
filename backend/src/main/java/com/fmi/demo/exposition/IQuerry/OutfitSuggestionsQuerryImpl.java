package com.fmi.demo.exposition.IQuerry;

import com.fmi.demo.domain.model.OutfitSuggestions;
import com.fmi.demo.domain.repository.OutfitSuggestionsRepository;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OutfitSuggestionsQuerryImpl implements IQuerry<OutfitSuggestions> {

    private OutfitSuggestionsRepository outfitSuggestionsRepository;
    @Override
    public OutfitSuggestions getById(String id) {
        if(!StringUtils.hasText(id) || !outfitSuggestionsRepository.existsById(id)){
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        return outfitSuggestionsRepository.getById(id).get();
    }

    @Override
    public List<OutfitSuggestions> getAll() {
        return outfitSuggestionsRepository.getAll();
    }

    @Override
    public List<OutfitSuggestions> getAllByUser(String userId) {
        return outfitSuggestionsRepository.getAll();
    }
}
