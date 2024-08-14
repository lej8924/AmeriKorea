package com.hana.amerikorea.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {

    private ObjectMapper objectMapper = new ObjectMapper();

    public Double extractFiledFromJsonOutput2(String json, String fieldName) {
        try {
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

    public double extractFieldFromJsonOutput1(String json, String fieldName) {
        try {
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

    public double extractFieldFromJsonOutput(String json, String fieldName) {
        try {
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
