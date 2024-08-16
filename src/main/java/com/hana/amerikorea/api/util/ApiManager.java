package com.hana.amerikorea.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApiManager implements ApplicationListener<ContextRefreshedEvent> {
    private final ApiUtil apiUtil;

    @Autowired
    public ApiManager(ApiUtil apiUtil) {
        this.apiUtil = apiUtil;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String apiKey = "oversea_dailyprice";
        Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("symb", "AAPL");

        apiUtil.callApiAndHandleResult(apiKey, additionalParams);
    }
}

