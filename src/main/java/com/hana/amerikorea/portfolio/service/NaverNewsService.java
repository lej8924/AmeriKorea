package com.hana.amerikorea.portfolio.service;

import com.hana.amerikorea.portfolio.dto.request.NaverNewsRequest;
import com.hana.amerikorea.portfolio.dto.response.NaverNewsResponse;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class NaverNewsService {

    private final WebClient webClient;

    @Value("${naver.url}")
    private String apiUrl;

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    public NaverNewsService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public NaverNewsResponse getNews(String query) {
        return webClient.get()
                .uri(apiUrl + "?query=" + query)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .retrieve()
                .bodyToMono(NaverNewsResponse.class)
                .block();
    }
}