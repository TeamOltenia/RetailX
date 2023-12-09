package com.fmi.demo.infra.jpa.mapper.custom;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CutomeMapperUtil {
    @ListToString
    public static String listToString(List<String> values){
        if(values==null || values.size()==0) {
            return "";
        }
        String result= values.stream().collect(Collectors.joining(","));
        return result;
    }

    @StringToList
    public static List<String> stringToList(String value){
        if(!StringUtils.hasText(value)){
            return new ArrayList<>();
        }
        return Arrays.asList(value.split(","));
    }

}
