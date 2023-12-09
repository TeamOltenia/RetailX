package com.fmi.demo.exposition.ICommand;

import com.fmi.demo.domain.model.Clothing;
import com.fmi.demo.domain.repository.ClothingRepository;
import com.fmi.demo.domain.repository.SavedOutfitsRepository;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import com.fmi.demo.exposition.services.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClothingCommandImpl implements ICommand<Clothing>{

    private final ClothingRepository clothingRepository;
    private final SavedOutfitsRepository savedOutfitsRepository;

    @Autowired
    private MinioService minioService;

    @Override
    public String save(Clothing body , String username) {
        fieldValidation(body);
        System.out.println("service!!!!!!!!!!!!");
        return clothingRepository.save(body);
    }



    @Override
    public String update(Clothing body, String id , String username) {
        if(!body.getId().equals(id) || !StringUtils.hasText(body.getId())){
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        fieldValidation(body);
        return clothingRepository.save(body);
    }

    @Override
    public void delete(String id , String username) {
        if(!StringUtils.hasText(id)){
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        if(!clothingRepository.existsById(id)){
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        String imageUrl= clothingRepository.getById(id).get().getImageUrl();
        clothingRepository.delete(id);
        savedOutfitsRepository.deleteClothingApparnces(username,id);
        minioService.deleteImage(imageUrl);

    }

    private void fieldValidation(Clothing body) {
//        if(!StringUtils.hasText(body.getName()) ||
//                ObjectUtils.isEmpty(body.getClothesBrand()) ||
//                ObjectUtils.isEmpty(body.getClothesCategory()) ||
//                body.getColors().size() <1
//        ) {
//            throw new CustomErrorHandler(ExceptionEnum.EMPTY_FIELD);
//        }
    }

}
