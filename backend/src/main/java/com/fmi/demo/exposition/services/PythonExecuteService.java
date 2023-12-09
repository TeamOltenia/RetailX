package com.fmi.demo.exposition.services;

import com.fmi.demo.domain.model.ConfirmationDataSet;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PythonExecuteService {

    @Value("${phython.server-url}")
    String pythonServerUrl;


    private final RestTemplate restTemplate;

    public PythonExecuteService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String proxiClassify(String imageUrl){
        String fastApiUrl = pythonServerUrl+"/classify/?image_url=" + imageUrl;
        try{
        ResponseEntity<String> response = restTemplate.getForEntity(fastApiUrl, String.class);
        return response.getBody();
        }
        catch (Exception e){
            throw new CustomErrorHandler(ExceptionEnum.FAILED_CLASIFICATION);
        }
    }

    public Boolean proxiSavePhoto(ConfirmationDataSet confirmationDataSet){
        String fastApiUrl = pythonServerUrl+"/savePhoto/?image_url=" + confirmationDataSet.getImageUrl()+"&"+"category="+ confirmationDataSet.getCategory();
        try{
            ResponseEntity<Boolean> response = restTemplate.getForEntity(fastApiUrl, Boolean.class);
            return response.getBody();
        }
        catch (Exception e){
            throw new CustomErrorHandler(ExceptionEnum.SAVE_FAILED_EXCEPITON);
        }
    }
}
