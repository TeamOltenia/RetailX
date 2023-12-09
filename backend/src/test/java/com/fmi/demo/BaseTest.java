package com.fmi.demo;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.boot.test.mock.mockito.MockBean;

public class BaseTest {

    public String extractContentFromPageJson(String string) throws Exception{
        JSONParser parser =new JSONParser(JSONParser.MODE_JSON_SIMPLE);
        Object obj = parser.parse(string);
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray array = (JSONArray) jsonObject.get("content");


        return String.valueOf(array);
    }
}
