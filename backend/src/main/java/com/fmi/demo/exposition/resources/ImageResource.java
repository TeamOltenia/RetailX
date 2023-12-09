package com.fmi.demo.exposition.resources;
import com.fmi.demo.exposition.services.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
public class ImageResource{


    @Autowired
    private MinioService minioService;

    @GetMapping("/images/{imageName}")
    public ResponseEntity<String> getImage(@PathVariable String imageName) throws Exception {
        return ResponseEntity.ok()
                .body(minioService.getImage(imageName));
    }
}