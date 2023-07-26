package com.spring.labs.domain.web.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class NaverSearchApi {

    @Value("${naver.client-id}")
    private String CLIENT_ID;

    @Value("${naver.client-secret}")
    private String CLIENT_SECRET;

    private static final String REQUEST_URL = "https://openapi.naver.com/v1/search/shop.json?";

    public List<Map<String, String>> getResult(String keyword, int display, String sort) {
        String encodedKeyword;
        try {
            encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String requestUrl = REQUEST_URL + "query=%s&display=%d&start=1&sort=%s".formatted(encodedKeyword, display, sort);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", CLIENT_ID);
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, new HttpEntity<>(headers), Map.class);
        return getJsonObject(responseEntity.getBody());
    }

    private <T> List<Map<String, String>> getJsonObject(T resultData) {
        List<Map<String, String>> extractedItems = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JSONArray documents;
        try {
            String jsonStr = mapper.writeValueAsString(resultData);
            JSONObject json = (JSONObject) JSONValue.parse(jsonStr);
            documents = (JSONArray) json.get("items");
            for (Object item : documents) {
                Map<String, String> map = new LinkedHashMap<>();

                JSONObject obj = (JSONObject) item;
                map.put("title", obj.get("title").toString().replace("<b>", "").replace("</b>", ""));
                map.put("link", (String) obj.get("link"));
                map.put("brand", (String) obj.get("brand"));
                map.put("lprice", (String) obj.get("lprice"));
                extractedItems.add(map);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return extractedItems;
    }

}
