package com.fmi.demo.exposition.resources;

import com.fmi.demo.domain.model.ConfirmationDataSet;
import com.fmi.demo.exposition.ICommand.ConfirmationDataSetCommandImpl;
import com.fmi.demo.exposition.IQuerry.ConfirmationDataSetQuerryImpl;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class AdminResource {

    @Autowired
    private  ConfirmationDataSetQuerryImpl confirmationDataSetQuerry;

    @Autowired
    private  ConfirmationDataSetCommandImpl confirmationDataSetCommand;

    @PutMapping(path = "/confirmationDataSet/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateConfirmationDataSet(@PathVariable("id") String id , @RequestBody ConfirmationDataSet confirmationDataSet){
        confirmationDataSetCommand.update(confirmationDataSet,id,getUser());
        return ResponseEntity.ok()
                .body("updated");
    }

    @GetMapping("/confirmationDataSet/allUnconfirmed")
    public ResponseEntity<List<ConfirmationDataSet>> getClothingById(){
        return ResponseEntity.ok()
                .body(confirmationDataSetQuerry.getAllUnconfirmed());
    }

    @DeleteMapping("/confirmationDataSet/{id}")
    public ResponseEntity<?> deleteConfirmationDataSet(@PathVariable("id") String id){
        confirmationDataSetCommand.delete(id);
        return ResponseEntity.ok().build();
    }

    private String getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (username != null) {
            System.out.println("Authenticated user's username is: " + username);
        } else {
            throw new CustomErrorHandler(ExceptionEnum.MISSING_USER);
        }
        return username;
    }

}
