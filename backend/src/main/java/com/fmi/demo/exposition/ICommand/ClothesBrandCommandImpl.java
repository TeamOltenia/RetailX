package com.fmi.demo.exposition.ICommand;

import com.fmi.demo.domain.model.ClothesBrand;
import com.fmi.demo.domain.repository.ClothesBrandRepository;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClothesBrandCommandImpl implements ICommand<ClothesBrand> {

    private final ClothesBrandRepository clothesBrandRepository;
    @Override
    public String save(ClothesBrand body, String username) {
        if(!StringUtils.hasText(body.getName()) || !StringUtils.hasText(body.getHomepage())){
            throw new CustomErrorHandler(ExceptionEnum.EMPTY_FIELD);
        }
        return clothesBrandRepository.save(body);
    }

    @Override
    public String update(ClothesBrand body, String id, String username) {
        if(!StringUtils.hasText(body.getName()) || !StringUtils.hasText(body.getHomepage()) ||
        !StringUtils.hasText(body.getId()) || !id.equals(body.getId())){
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }

        return clothesBrandRepository.save(body);
    }

    @Override
    public void delete(String id, String username) {
        if(!StringUtils.hasText(id) || !clothesBrandRepository.existsById(id)){
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        clothesBrandRepository.delete(id);

    }
}
