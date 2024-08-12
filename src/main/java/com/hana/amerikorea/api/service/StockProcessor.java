package com.hana.amerikorea.api.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class StockProcessor {

    @Autowired
    private ApiService apiService;

    private static final String PURCHASE_PRICE_API_KEY = "inquire-daily-itemchartprice";
    private static final String CURRENT_PRICE_API_KEY = "inquire-price";
    private static final String CASH_DIVIDEND_API_KEY = "dividend";

    public double getCurrentPrice(String stockCode) {
        Map<String, String> params = new HashMap<>();
        params.put("fid_cond_mrkt_div_code", "J");
        params.put("fid_input_iscd", stockCode);
        return callApiAndExtractDouble(CURRENT_PRICE_API_KEY,"stck_prpr",params);
    }

    public double getPurchasePrice(String stockCode, String purchaseDate) {
        Map<String, String > params = new HashMap<>();
        params.put("fid_cond_mrkt_div_code", "J");
        params.put("fid_input_iscd", stockCode);
        params.put("fid_input_date_1", purchaseDate);
        params.put("fid_input_date_2", purchaseDate);
        return callApiAndExtractDouble(PURCHASE_PRICE_API_KEY, "stck_oprc", params);
    }

    public double getCashDividend(String stockCode) {
        Map<String, String> params = new HashMap<>();
        params.put("f_dt", "2022");
        params.put("t_dt", "2030");
        params.put("sht_cd", stockCode);
        return callApiAndExtractDouble(CASH_DIVIDEND_API_KEY, "per_sto_divi_amt", params);
    }

    public Double getCashDividendMonth(String stockCode) {
        Map<String, String> params = new HashMap<>();
        params.put("f_dt", "2022");
        params.put("t_dt", "2030");
        params.put("sht_cd", stockCode);
        return callApiAndExtractDouble(CASH_DIVIDEND_API_KEY, "record_date", params);
    }

    private double callApiAndExtractDouble(String apiKey, String fieldName, Map<String, String> additionalParams) {
        Mono<String> responseMono = apiService.callApi(apiKey, additionalParams);
        String response = responseMono.block();
        System.out.println(response);

        if (apiKey.equals(PURCHASE_PRICE_API_KEY)) {
            return extractFieldFromJsonOutput1(response, fieldName);
        } else if (apiKey.equals(CURRENT_PRICE_API_KEY)) {
            return extractFieldFromJsonOutput(response, fieldName);
        } else if (apiKey.equals(CASH_DIVIDEND_API_KEY)) {
            return extractFiledFromJsonOutput2(response, fieldName);
        } else {
            throw new RuntimeException("적용되는 API가 아닙니다");
        }
    }
    private Double extractFiledFromJsonOutput2(String json, String fieldName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            // output1 배열 가져오기
            JsonNode outputArray = rootNode.path("output1");

            // 배열의 첫 번째 객체 가져오기
            if (outputArray.isArray() && outputArray.size() > 0) {
                JsonNode firstObject = outputArray.get(0);

                JsonNode fieldNode = firstObject.path(fieldName);
                if (fieldName.equals("record_date")) {
                    // record_date에서 월(MM) 값만 추출
                    String recordDate = fieldNode.asText();
                    if (recordDate.length() >= 6) {
                        String monthString = recordDate.substring(4, 6); // "20240326"에서 "03" 추출
                        return Double.valueOf(monthString);
                    } else {
                        System.out.println("record_date format is invalid");
                        return 0.0;
                    }
                } else {
                    // 기본적으로 Double 값으로 반환
                    if (fieldNode.isMissingNode()) {
                        System.out.println("Field " + fieldName + " is not found");
                        return 0.0;
                    }
                    return fieldNode.asDouble();
                }
            } else {
                System.out.println("output1 array is empty or not found");
                return 0.0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse JSON data", e);
        }
    }

    private double extractFieldFromJsonOutput1(String json, String fieldName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            JsonNode output1Node = rootNode.path("output1");
            JsonNode fieldNode = output1Node.path(fieldName);

            if(fieldNode.isMissingNode()) {
                System.out.println("field "+ fieldName+"  is not founded");
                return 0.0;
            }
            return fieldNode.asDouble();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    private double extractFieldFromJsonOutput(String json, String fieldName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            JsonNode output1Node = rootNode.path("output");
            JsonNode fieldNode = output1Node.path(fieldName);

            if (fieldNode.isMissingNode()) {
                System.out.println("field " + fieldName + "  is not founded");
                return 0.0;
            }
            return fieldNode.asDouble();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    }

