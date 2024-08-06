package com.hana.amerikorea.api.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana.amerikorea.api.model.ApiInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApiConfig {
    public static final String REST_BASE_URL="https://openapi.koreainvestment.com:9443";
    public static final String WS_BASE_URL="ws://ops.koreainvestment.com:9443";
    public static final String APPKEY = "PSA60aQNb0Bb4QC28GYKzwvzg09Grn4p6R43";
    public static final String APPSECRET = "EZw6CsYLPGUpPoO/AZMamCoZIByDzkBoQmn0gS6uD3tw9wz8uDH0JoQEQLGeFPpq448D5+nl8yFJFCvcOGZCrCPrQlhLjU56H37db8DRy5gWA4LUNYiTQHF9wCIpwXzFBguvE4CY4oTRNrEdiVYum8w8deObyObIsnxVH46YXD/EMpiP1fs=";

    private final Map<String, ApiInfo> apiInfoMap = new HashMap<>();

    @PostConstruct
    private void initApiInfo() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String, ApiInfo>> typeRef = new TypeReference<Map<String, ApiInfo>>() {};
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("api-config.json");

        try {
            Map<String, ApiInfo> loadedMap = mapper.readValue(inputStream, typeRef);
            apiInfoMap.putAll(loadedMap);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load API config",e);
        }

    }

    public ApiInfo getApiInfo(String key) {
        return apiInfoMap.get(key);
    }

}

