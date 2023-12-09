package com.fmi.demo.infra.repository;

import com.fmi.demo.domain.model.Clothing;
import com.fmi.demo.domain.repository.ClothingRepository;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import com.fmi.demo.infra.jpa.ClothingJPA;
import com.fmi.demo.infra.jpa.mapper.ClothingJPAMapper;
import com.fmi.demo.infra.repository.jpa.ClothingJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Repository
public class ClothingRepositoryImpl implements ClothingRepository {

    private final ClothingJPARepository clothingJPARepository;

    private final ClothingJPAMapper clothingJPAMapper;

    @Override
    public String save(Clothing clothing) {
        System.out.println("repooo!!!!!!!!!!!!!!");
        ClothingJPA clothingJPA= clothingJPARepository.save(clothingJPAMapper.toDto(clothing));
        return clothingJPA.getId();
    }

    @Override
    public void delete(String id) {
        clothingJPARepository.deleteById(id);
    }

    @Override
    public Optional<Clothing> getById(String id) {
        Optional<ClothingJPA> clothingOptional =  clothingJPARepository.findById(id);
        if(clothingOptional.isEmpty()){
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        return Optional.of(clothingJPAMapper.toDomain(clothingOptional.get()));
    }

    @Override
    public boolean existsById(String id) {
        return clothingJPARepository.existsById(id);
    }

    @Override
    public List<Clothing> getAll(String userId) {
        return  clothingJPARepository.findClothingJPAByUserIdEquals(userId).stream().map(clothingJPAMapper::toDomain).collect(Collectors.toList());
    }
}
