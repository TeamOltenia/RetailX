package com.fmi.demo.exposition.IQuerry;

import com.fmi.demo.domain.model.ClothesBrand;
import com.fmi.demo.domain.repository.ClothesBrandRepository;
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
public class ClothesBrandQuerryImpl implements IQuerry<ClothesBrand> {
    private final ClothesBrandRepository clothesBrandRepository;

    @Override
    public ClothesBrand getById(String id) {
        if(!StringUtils.hasText(id) || !clothesBrandRepository.existsById(id)){
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        return clothesBrandRepository.getById(id).get();
    }

    @Override
    public List<ClothesBrand> getAll() {
        return clothesBrandRepository.getAll();
    }

    @Override
    public List<ClothesBrand> getAllByUser(String userId) {
        return null;
    }
}
