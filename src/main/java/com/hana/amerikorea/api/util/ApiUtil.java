package com.hana.amerikorea.api.util;

import com.hana.amerikorea.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ApiUtil {
    private final ApiService apiService;

    @Autowired
    public ApiUtil(ApiService apiService) {
        this.apiService=apiService;
    }

    public void callApiAndHandleResult(String apiKey, Map<String, String> additionalParams) {
        Mono<String> response = apiService.callApi(apiKey,additionalParams);

        response.subscribe(
                result-> {
                    System.out.println("API 호출 성공: "+result);
                    String apiResponse = result;
                    handleApiResponse(apiResponse);
                },
                error -> System.err.println("API 호출 실패: "+error.getMessage())
        );
    }
    private void handleApiResponse(String apiResponse) {
        System.out.println("API Response: " + apiResponse);
        //API 응답 처리 로직
    }
}