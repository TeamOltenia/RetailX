package com.fmi.demo.domain.repository;

import com.fmi.demo.domain.model.ConfirmationDataSet;

import java.util.List;

public interface ConfirmationDataSetRepository {

    List<ConfirmationDataSet> getAllUnconfirmed();

    String saveConfirmationDataSet(ConfirmationDataSet confirmationDataSet);

    void deleteConfirmationDataSet(String id);

}
