package com.fmi.demo.infra.repository;

import com.fmi.demo.domain.model.ClothesBrand;
import com.fmi.demo.domain.repository.ClothesBrandRepository;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import com.fmi.demo.infra.jpa.ClothesBrandJPA;
import com.fmi.demo.infra.jpa.mapper.ClothesBrandJPAMapper;
import com.fmi.demo.infra.repository.jpa.ClothesBrandJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Repository
public class ClothesBrandRepositoryImpl implements ClothesBrandRepository {
    private final ClothesBrandJPAMapper clothesBrandJPAMapper;

    private final ClothesBrandJPARepository clothesBrandJPARepository;

    @Override
    public String save(ClothesBrand clothing) {
        return clothesBrandJPARepository.save(clothesBrandJPAMapper.toDto(clothing)).getId();
    }

    @Override
    public void delete(String id) {
        clothesBrandJPARepository.deleteById(id);
    }

    @Override
    public Optional<ClothesBrand> getById(String id) {
        Optional<ClothesBrandJPA> clothesBrandJPA = clothesBrandJPARepository.findById(id);
        if(clothesBrandJPA.isEmpty()){
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        return Optional.of(clothesBrandJPAMapper.toDomain(clothesBrandJPA.get()));
    }

    @Override
    public boolean existsById(String id) {
        return clothesBrandJPARepository.existsById(id);
    }

    @Override
    public List<ClothesBrand> getAll() {
        return clothesBrandJPARepository.findAll().stream().map(clothesBrandJPAMapper::toDomain).toList();
    }
}
