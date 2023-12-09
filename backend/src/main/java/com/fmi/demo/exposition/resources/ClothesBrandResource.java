package com.fmi.demo.exposition.resources;

import com.fmi.demo.domain.model.ClothesBrand;
import com.fmi.demo.domain.model.ClothesBrand;
import com.fmi.demo.exposition.ICommand.ICommand;
import com.fmi.demo.exposition.IQuerry.IQuerry;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import com.fmi.demo.infra.repository.ClothesBrandRepositoryImpl;
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
@RequestMapping(value = "/api/v1/clothingBrand", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class ClothesBrandResource {

    @Autowired
    private ICommand<ClothesBrand> clothesCategoryICommand;

    @Autowired
    private IQuerry<ClothesBrand> clothesCategoryIQuerry;


    @PostMapping("")
    public ResponseEntity<ClothesBrand> createAuthor(@RequestBody ClothesBrand author) throws Exception{
        String id= clothesCategoryICommand.save(author,getUser());
        final var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + id).toUriString();
        return ResponseEntity.created(new URI(uri))
                .body(clothesCategoryIQuerry.getById(id));
    }

    @PutMapping(path = "/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClothesBrand> updateAuthor(@PathVariable("id") String id , @RequestBody ClothesBrand ClothesBrand){
        String objectId= clothesCategoryICommand.update(ClothesBrand,id,getUser());
        return ResponseEntity.ok()
                .body(clothesCategoryIQuerry.getById(objectId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClothesBrand>> getAuthorAll(){
        return ResponseEntity.ok()
                .body(clothesCategoryIQuerry.getAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ClothesBrand> getAuthorById(@PathVariable("id") String id){
        return ResponseEntity.ok()
                .body(clothesCategoryIQuerry.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") String id){
        clothesCategoryICommand.delete(id,getUser());
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
