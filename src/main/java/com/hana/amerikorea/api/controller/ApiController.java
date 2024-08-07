package com.hana.amerikorea.api.controller;

import com.hana.amerikorea.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
public class ApiController {

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/api/call")
    public Mono<String> callApi(@RequestParam String apiKey,
                                @RequestParam Map<String, String> queryParams) {
        return apiService.callApi(apiKey, queryParams);
    }

}