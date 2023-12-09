package com.fmi.demo.exposition.resources;

import com.fmi.demo.domain.model.enums.EnumDto;
import com.fmi.demo.domain.model.enums.OccasionsEnum;
import com.fmi.demo.domain.model.enums.SeasonsEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/enums", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class EnumResource {
    @GetMapping("")
    private ResponseEntity<Map<String,List<EnumDto>>> getAllEnums(){
        HashMap<String,List<EnumDto>> result = new HashMap<>();
        result.put(OccasionsEnum.class.getSimpleName(), Arrays.stream(OccasionsEnum.values())
                .map(enums -> EnumDto.builder().label(enums.toString()).value(enums.getName()).build()).toList());
        result.put(SeasonsEnum.class.getSimpleName(), Arrays.stream(SeasonsEnum.values())
                .map(enums -> EnumDto.builder().label(enums.toString()).value(enums.getName()).build()).toList());

        return ResponseEntity.ok()
                .body(result);
    }
}
