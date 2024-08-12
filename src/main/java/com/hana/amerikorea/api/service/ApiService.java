package com.hana.amerikorea.api.service;

import com.hana.amerikorea.api.config.ApiConfig;

import com.hana.amerikorea.api.config.AppProperties;
import com.hana.amerikorea.api.model.ApiInfo;
import com.hana.amerikorea.api.util.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiService {

    private final ApiConfig apiConfig;
    private final WebClient webClient;

    private final TokenManager tokenManager;
    private final AppProperties appProperties;

    @Autowired
    public ApiService(TokenManager tokenManager, ApiConfig apiConfig, WebClient.Builder webClientBuilder, AppProperties appProperties) {
        this.apiConfig=apiConfig;
        this.webClient = webClientBuilder.baseUrl(ApiConfig.REST_BASE_URL).build();
        this.tokenManager = tokenManager;
        this.appProperties=appProperties;

    }

    public Mono<String> callApi(String apiKey) {
        return callApi(apiKey, new HashMap<>());
    }


    public Mono<String> callApi(String apiKey, Map<String, String> additionalParam) {
        ApiInfo apiInfo = apiConfig.getApiInfo(apiKey);
        if(apiInfo==null) {
            return Mono.error(new IllegalArgumentException("API Key를 다시 한 번 확인해주세요. "+apiKey));
        }

        Map<String, String> queryParams = new HashMap<>(apiInfo.getDefaultQueryParams());
        queryParams.putAll(additionalParam);

        WebClient.RequestHeadersUriSpec<?> uriSpec = webClient.get();
        WebClient.RequestHeadersSpec<?> headersSpec = uriSpec.uri(uriBuilder -> {
            uriBuilder.path(apiInfo.getUrl());
            queryParams.forEach(uriBuilder::queryParam);
            return uriBuilder.build();
        });

        return headersSpec
                .header("Content-Type", "application/json; utf-8")
                .header("authorization", "Bearer " + tokenManager.getAccessToken())
                .header("appKey", appProperties.getAppkey())
                .header("appSecret", appProperties.getAppsecret())
                .header("tr_id", apiInfo.getTrId())
                .retrieve()
                .bodyToMono(String.class);
    }
}

