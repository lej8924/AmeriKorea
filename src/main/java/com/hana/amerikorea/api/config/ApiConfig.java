package com.hana.amerikorea.api.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana.amerikorea.api.model.ApiInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class ApiConfig {
    public static final String REST_BASE_URL="https://openapi.koreainvestment.com:9443";

    private final Map<String, ApiInfo> apiInfoMap = new HashMap<>();

    private boolean isLoaded = false;

    @PostConstruct
    private void initApiInfo() {
        if(isLoaded) {
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String, ApiInfo>> typeRef = new TypeReference<Map<String, ApiInfo>>() {};
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("api-config.json");

        try {
            Map<String, ApiInfo> loadedMap = mapper.readValue(inputStream, typeRef);

            if(loadedMap==null || loadedMap.isEmpty()) {
                throw new RuntimeException("불러올 수 없는 API입니다");
            }
            apiInfoMap.putAll(loadedMap);
            isLoaded=true;
        } catch (IOException e) {
            throw new RuntimeException("API 호출 실패",e);

        }

    }

    public ApiInfo getApiInfo(String key) {
        return apiInfoMap.get(key);
    }

}

