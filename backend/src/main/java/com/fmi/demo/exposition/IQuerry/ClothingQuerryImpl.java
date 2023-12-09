package com.fmi.demo.exposition.IQuerry;

import com.fmi.demo.domain.model.Clothing;
import com.fmi.demo.domain.repository.ClothingRepository;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ErrorDetails;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import com.fmi.demo.exposition.services.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClothingQuerryImpl implements ClothingIQuerry {

    private final MinioService minioService;

    private final  ClothingRepository clothingRepository;
    @Override
    public Clothing getById(String id) {
        if(!StringUtils.hasText(id)){
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        Optional<Clothing> optionalClothing = clothingRepository.getById(id);
        if(optionalClothing.isEmpty()){
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        Clothing clothing = optionalClothing.get();
        if(StringUtils.hasText(clothing.getImageUrl())){
            clothing.setImage(minioService.getImage(clothing.getImageUrl()));
        }
        return optionalClothing.get();
    }

    @Override
    public List<Clothing> getAll(String userId) {
        if(!StringUtils.hasText(userId)){
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        List<Clothing> clothingList = clothingRepository.getAll(userId);
        for( Clothing x : clothingList){
            if(StringUtils.hasText(x.getImageUrl())){
                x.setImage(minioService.getImage(x.getImageUrl()));
            }
        }
        return clothingList;

    }
}
