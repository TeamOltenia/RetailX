package com.fmi.demo.exposition.IQuerry;

import com.fmi.demo.domain.model.Clothing;
import com.fmi.demo.domain.model.ConfirmationDataSet;
import com.fmi.demo.domain.repository.ConfirmationDataSetRepository;
import com.fmi.demo.exposition.services.MinioService;
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
public class ConfirmationDataSetQuerryImpl {

    private final MinioService minioService;
    private final ConfirmationDataSetRepository confirmationDataSetRepository;
    public List<ConfirmationDataSet> getAllUnconfirmed(){
        List<ConfirmationDataSet> confirmationDataSets = confirmationDataSetRepository.getAllUnconfirmed();
        for( ConfirmationDataSet x : confirmationDataSets){
            if(StringUtils.hasText(x.getImageUrl())){
                x.setImage(minioService.getImage(x.getImageUrl()));
            }
        }
        return confirmationDataSets;
    }
}
