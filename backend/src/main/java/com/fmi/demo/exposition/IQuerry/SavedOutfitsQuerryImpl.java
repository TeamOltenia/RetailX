package com.fmi.demo.exposition.IQuerry;

import com.fmi.demo.domain.model.SavedOutfits;
import com.fmi.demo.domain.repository.ClothingRepository;
import com.fmi.demo.domain.repository.SavedOutfitsRepository;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SavedOutfitsQuerryImpl implements IQuerry<SavedOutfits> {

    private final SavedOutfitsRepository savedOutfitsRepository;

    @Autowired
    private ClothingIQuerry clothingIQuerry;
    @Override
    public SavedOutfits getById(String id) {
        if(!StringUtils.hasText(id) || !savedOutfitsRepository.existsById(id)){
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        SavedOutfits savedOutfits = savedOutfitsRepository.getById(id).get();
        savedOutfits.getClothesIds().stream().forEach(value->{
            savedOutfits.getClothes().add(clothingIQuerry.getById(value));
        });
        return savedOutfits;
    }

    @Override
    public List<SavedOutfits> getAll() {
      //  return savedOutfitsRepository.getAll();
        return null;
    }

    @Override
    public List<SavedOutfits> getAllByUser(String userId) {
        List<SavedOutfits> savedOutfits = savedOutfitsRepository.getAll(userId);
        savedOutfits.forEach(savedOutfit ->{
            savedOutfit.getClothesIds().forEach(value->{
                savedOutfit.getClothes().add(clothingIQuerry.getById(value));
            });
        });
        return savedOutfits;
    }
}
