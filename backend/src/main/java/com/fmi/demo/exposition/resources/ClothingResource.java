package com.fmi.demo.exposition.resources;


import com.fmi.demo.domain.model.Clothing;
import com.fmi.demo.domain.model.ConfirmationDataSet;
import com.fmi.demo.exposition.ICommand.ConfirmationDataSetCommandImpl;
import com.fmi.demo.exposition.ICommand.ICommand;
import com.fmi.demo.exposition.IQuerry.ClothingIQuerry;
import com.fmi.demo.exposition.exceptions.CustomErrorHandler;
import com.fmi.demo.exposition.exceptions.ExceptionEnum;
import com.fmi.demo.exposition.services.MinioService;
import com.fmi.demo.exposition.services.PythonExecuteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/clothing", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class ClothingResource {

    @Autowired
    private ICommand<Clothing> clothingICommand;

    @Autowired
    private ClothingIQuerry clothingIQuerry;

    @Autowired
    private PythonExecuteService pythonExecuteService;

    @Autowired
    private ConfirmationDataSetCommandImpl confirmationDataSetCommand;

    @Autowired
    private MinioService minioService;

    @PostMapping("")
    public ResponseEntity<Clothing> createClothing(@RequestBody Clothing author) throws Exception{
        String id= clothingICommand.save(author,getUser());
        final var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + id).toUriString();
        return ResponseEntity.created(new URI(uri))
                .body(clothingIQuerry.getById(id));
    }

    @PutMapping(path = "/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Clothing> updateClothing(@PathVariable("id") String id , @RequestBody Clothing clothing){
        String objectId= clothingICommand.update(clothing,id,getUser());
        return ResponseEntity.ok()
                .body(clothingIQuerry.getById(objectId));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Clothing> getClothingById(@PathVariable("id") String id){
        return ResponseEntity.ok()
                .body(clothingIQuerry.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Clothing>> getClothingById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (username != null) {
            System.out.println("Authenticated user's username is: " + username);
        } else {
            throw new CustomErrorHandler(ExceptionEnum.MISSING_USER);
        }
        return ResponseEntity.ok()
                .body(clothingIQuerry.getAll(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClothing(@PathVariable("id") String id){
        clothingICommand.delete(id,getUser());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/classify")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = minioService.storeImage(file);
            String category = pythonExecuteService.proxiClassify(imageUrl);
            category = category.substring(1, category.length() - 1);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            if (username != null) {
                System.out.println("Authenticated user is: " + username);
            } else {
                throw new CustomErrorHandler(ExceptionEnum.MISSING_USER);
            }
            Clothing clothing = Clothing.builder().imageUrl(imageUrl).clothesCategory(category).name(category+"-Temp").id(null).userId(username).build();
            String id = clothingICommand.save(clothing,getUser());
            confirmationDataSetCommand.save(mapClothingtoConfirmation(clothing),getUser());
            return ResponseEntity.ok().body(id);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while storing the image.");
        }
    }


    private ConfirmationDataSet mapClothingtoConfirmation(Clothing clothing){
        return ConfirmationDataSet.builder().imageUrl(clothing.getImageUrl()).category(clothing.getClothesCategory()).build();
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
