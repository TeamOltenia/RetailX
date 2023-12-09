package com.fmi.demo.exposition.ICommand;

import com.fmi.demo.domain.model.ConfirmationDataSet;
import com.fmi.demo.domain.repository.ConfirmationDataSetRepository;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import com.fmi.demo.exposition.services.PythonExecuteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ConfirmationDataSetCommandImpl {

    private final ConfirmationDataSetRepository confirmationDataSetRepository;

    @Autowired
    private  PythonExecuteService pythonExecuteService;
    public String save(ConfirmationDataSet body, String username) {
        body.setAddedUserId(username);
        body.setConfirmed(Boolean.FALSE);
        return confirmationDataSetRepository.saveConfirmationDataSet(body);
    }

    public String update(ConfirmationDataSet body, String id, String username) {
        if(!body.getId().equals(id)){
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        body.setConfirmed(Boolean.TRUE);
        body.setConfirmedUserId(username);
        String confirmationId =  confirmationDataSetRepository.saveConfirmationDataSet(body);
        pythonExecuteService.proxiSavePhoto(body);
        return confirmationId;
    }

    public void delete(String id) {
        if(!StringUtils.hasText(id)){
            throw new CustomErrorHandler(ExceptionEnum.EMPTY_FIELD);
        }
        confirmationDataSetRepository.deleteConfirmationDataSet(id);
    }
}
