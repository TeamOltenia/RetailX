package com.fmi.demo.exposition.resources;

import com.fmi.demo.domain.model.SavedOutfits;
import com.fmi.demo.domain.model.SavedOutfits;
import com.fmi.demo.exposition.ICommand.ICommand;
import com.fmi.demo.exposition.IQuerry.IQuerry;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/savedOutfits", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class SavedOutfitsResource {

    @Autowired
    private ICommand<SavedOutfits> savedOutfitsICommand;

    @Autowired
    private IQuerry<SavedOutfits> savedOutfitsIQuerry;

    @PostMapping("")
    public ResponseEntity<SavedOutfits> createSavedOutfit(@RequestBody SavedOutfits author) throws Exception{
        String id= savedOutfitsICommand.save(author,getUser());
        final var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + id).toUriString();
        return ResponseEntity.created(new URI(uri))
                .body(savedOutfitsIQuerry.getById(id));
    }

    @PutMapping(path = "/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SavedOutfits> updateSavedOutfit(@PathVariable("id") String id , @RequestBody SavedOutfits clothing){

        String objectId= savedOutfitsICommand.update(clothing,id,getUser());
        return ResponseEntity.ok()
                .body(savedOutfitsIQuerry.getById(objectId));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<SavedOutfits> getSavedOutfitById(@PathVariable("id") String id){
        return ResponseEntity.ok()
                .body(savedOutfitsIQuerry.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SavedOutfits>> getSavedOutfitById(){
        return ResponseEntity.ok()
                .body(savedOutfitsIQuerry.getAllByUser(getUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSavedOutfit(@PathVariable("id") String id){
        savedOutfitsICommand.delete(id,getUser());
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
