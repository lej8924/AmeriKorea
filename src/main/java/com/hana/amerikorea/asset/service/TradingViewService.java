package com.hana.amerikorea.asset.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TradingViewService {

    private final WebClient webClient;

    public TradingViewService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://unpkg.com").build();
    }

    public Mono<String> getTradingViewChartScript() {
        return this.webClient.get()
                .uri("/lightweight-charts/dist/lightweight-charts.standalone.production.js")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getCandlestickData() {
        // Replace with your actual API URL and path
        return this.webClient.get()
                .uri("https://your-api-url.com/api/candlestick-data")
                .retrieve()
                .bodyToMono(String.class);
    }
}