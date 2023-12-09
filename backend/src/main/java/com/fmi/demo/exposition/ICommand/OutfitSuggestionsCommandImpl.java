package com.fmi.demo.exposition.ICommand;

import com.fmi.demo.domain.model.OutfitSuggestions;
import com.fmi.demo.domain.repository.OutfitSuggestionsRepository;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OutfitSuggestionsCommandImpl implements ICommand<OutfitSuggestions> {

    private OutfitSuggestionsRepository outfitSuggestionsRepository;
    @Override
    public String save(OutfitSuggestions body , String username) {
        if(body.getClothesIds().isEmpty() || !StringUtils.hasText(body.getName()) || !ObjectUtils.isEmpty(body.getSeason()) ||
        !ObjectUtils.isEmpty(body.getWheather()) || !ObjectUtils.isEmpty(body.getOccasion())){
            throw new CustomErrorHandler(ExceptionEnum.EMPTY_FIELD);
        }
        return outfitSuggestionsRepository.save(body);
    }

    @Override
    public String update(OutfitSuggestions body, String id,String username) {

        if(body.getClothesIds().isEmpty() || !StringUtils.hasText(body.getName()) || !ObjectUtils.isEmpty(body.getSeason()) ||
                !ObjectUtils.isEmpty(body.getWheather()) || !ObjectUtils.isEmpty(body.getOccasion())){
            throw new CustomErrorHandler(ExceptionEnum.EMPTY_FIELD);
        }
        if(!StringUtils.hasText(body.getId()) || !id.equals(body.getId()) || !outfitSuggestionsRepository.existsById(id)){
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        return outfitSuggestionsRepository.save(body);
    }

    @Override
    public void delete(String id,String username) {
        if(!StringUtils.hasText(id)){
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        outfitSuggestionsRepository.delete(id);
    }
}
