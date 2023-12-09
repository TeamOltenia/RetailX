package com.fmi.demo.infra.repository;

import com.fmi.demo.domain.model.ConfirmationDataSet;
import com.fmi.demo.domain.repository.ConfirmationDataSetRepository;
import com.fmi.demo.infra.jpa.ConfirmationDataSetJPA;
import com.fmi.demo.infra.jpa.mapper.ConfirmationDataSetJPAMapper;
import com.fmi.demo.infra.repository.jpa.ConfirmationDataSetJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Repository
public class ConfirmationDataSetRepositoryImpl implements ConfirmationDataSetRepository {

    private final ConfirmationDataSetJPARepository confirmationDataSetJPARepository;

    private final ConfirmationDataSetJPAMapper confirmationDataSetJPAMapper;

    @Override
    public List<ConfirmationDataSet> getAllUnconfirmed() {
        List<ConfirmationDataSetJPA> confirmationDataSetJPAS = confirmationDataSetJPARepository
                .findConfirmationDataSetJPAByConfirmed(Boolean.FALSE);
        if(ObjectUtils.isEmpty(confirmationDataSetJPAS)){
            return Collections.emptyList();
        }
        else {
            return confirmationDataSetJPAS.stream().map(confirmationDataSetJPAMapper::toDomain).toList();
        }
    }

    @Override
    public String saveConfirmationDataSet(ConfirmationDataSet confirmationDataSet) {
        ConfirmationDataSetJPA confirmationDataSetJPA = confirmationDataSetJPAMapper.toDto(confirmationDataSet);
        return confirmationDataSetJPARepository.save(confirmationDataSetJPA).getId();
    }

    @Override
    public void deleteConfirmationDataSet(String id) {
        confirmationDataSetJPARepository.deleteById(id);
    }
}
