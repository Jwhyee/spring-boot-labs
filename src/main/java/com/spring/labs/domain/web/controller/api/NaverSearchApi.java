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

    private final int DISPLAY = 5;
    private final String SORT = "sim";

    private static final String REQUEST_URL = "https://openapi.naver.com/v1/search/shop.json?";
    private static final String PARAMETER_INFO = "query=%s&display=%d&start=1&sort=%s";

    /**
     * @apiNote 네이버에 요청을 보내는 메소드
     * @param keyword 네이버 쇼핑에 검색할 키워드
     * */
    public List<Map<String, String>> getResult(String keyword) {
        // 키워드를 UTF-8 인코딩
        String encodedKeyword = keyword;
        try {
            encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // 요청 URL에 파라미터와 그에 맞는 값 매핑
        String requestUrl = REQUEST_URL + PARAMETER_INFO.formatted(encodedKeyword, DISPLAY, SORT);

        // 요청을 보내기 위한 RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // 공식 문서에 나와 있듯이, 요청 헤더에 클라이언트 아이디 및 시크릿 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", CLIENT_ID);
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);

        // RestTemplate을 이용해 필요한 정보를 담아 요청을 보내 값을 String 타입으로 반환(exchange) 받음
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        // RestTemplate을 통해 반환 받은 Map을 JSON 형식으로 변환 후 가공한 다음 Controller에 반환
        return getJsonObject(responseEntity.getBody());
    }

    /**
     * @apiNote RestTemplate을 통해 받아온 데이터를 가공하는 메소드
     * @param resultData RestTemplate을 통해 받아온 JSON 형태의 문자열 데이터
     * */
    private List<Map<String, String>> getJsonObject(String resultData) {
        // Key : Value 짝으로 된 하나의 검색 결과
        List<Map<String, String>> extractedItems = new ArrayList<>();

        // 앞서 RestTemplate을 통해 반환 받은 문자열을 JSONObject(Json 객체)로 변환
        JSONObject json = (JSONObject) JSONValue.parse(resultData);

        // 변환한 JSON 객체에서 키 값이 item인 부분만 가져옴
        JSONArray documents = (JSONArray) json.get("items");

        // item에 담긴 검색 결과를 탐색하며, 필요한 데이터만 가공
        for (Object item : documents) {

            // 필요한 데이터만 저장하기 위해 Map 생성
            Map<String, String> map = new LinkedHashMap<>();

            // JSONArray에서 꺼내온 객체가 Object 타입이기 때문에 JSONObject로 변환
            JSONObject obj = (JSONObject) item;

            // 필요한 값들만 가져와 map에 추가
            map.put("title", (String) obj.get("title"));
            map.put("link", (String) obj.get("link"));
            map.put("brand", (String) obj.get("brand"));
            map.put("lprice", (String) obj.get("lprice"));

            // 완성된 map을 최종 결과물 List에 추가
            extractedItems.add(map);
        }
        return extractedItems;
    }

}
