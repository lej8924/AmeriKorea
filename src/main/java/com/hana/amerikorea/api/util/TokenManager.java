package com.hana.amerikorea.api.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana.amerikorea.api.config.ApiConfig;
import com.hana.amerikorea.api.config.AppProperties;
import com.hana.amerikorea.api.model.OauthInfo;
import com.hana.amerikorea.api.model.TokenInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class TokenManager {
    private final WebClient webClient;
    public static String ACCESS_TOKEN;
    public static long last_auth_time=0;
    private final AppProperties appProperties;
    private static final long EXPIRATION_TIME = 86400000;
    private static final String TOKEN_FILE="src/main/resources/access_token.json";
    private final Lock lock = new ReentrantLock();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TokenManager(WebClient.Builder webClientBuilder, AppProperties appProperties) {
        this.webClient = webClientBuilder.baseUrl(ApiConfig.REST_BASE_URL).build();
        this.appProperties=appProperties;
        loadTokenFromFile();
    }

    public String getAccessToken() {
        lock.lock();
        try {
            long currentTIme = System.currentTimeMillis();
            if(ACCESS_TOKEN==null||(currentTIme-last_auth_time)>=EXPIRATION_TIME) {
                ACCESS_TOKEN=generateAccessToken();
                last_auth_time=currentTIme;
                saveTokenToFile();
                System.out.println("generated ACCESS_TOKEN: "+ACCESS_TOKEN);
            } else {
                System.out.println("Reusing existing ACESS_TOKEN: " +ACCESS_TOKEN);
            }
            return ACCESS_TOKEN;
        } finally {
            lock.unlock();
        }
    }

    private String generateAccessToken() {
        String url = ApiConfig.REST_BASE_URL+"/oauth2/tokenP";
        OauthInfo bodyOauthInfo = new OauthInfo();
        bodyOauthInfo.setGrant_type("client_credentials");
        bodyOauthInfo.setAppkey(appProperties.getAppkey());
        bodyOauthInfo.setAppsecret(appProperties.getAppsecret());


        Mono<TokenInfo> mono = webClient.post()
                .uri(url)
                .bodyValue(bodyOauthInfo)
                .retrieve()
                .bodyToMono(TokenInfo.class);

        TokenInfo tokenInfo = mono.block();
        if(tokenInfo==null || tokenInfo.getAccess_token()==null) {
            throw new RuntimeException("토큰을 가져올 수 없습니다");
        }
        return tokenInfo.getAccess_token();
    }

    private void saveTokenToFile() {
        try {
            Map<String, Object> tokenData = new HashMap<>();
            tokenData.put("access_token", ACCESS_TOKEN);
            tokenData.put("last_auth_time", last_auth_time);
            objectMapper.writeValue(new File(TOKEN_FILE), tokenData);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("토큰을 파일에 저장하는 동안 오류가 발생했습니다", e);
        }
    }

    private void loadTokenFromFile() {
        try {
            File file = new File(TOKEN_FILE);
            if (file.exists()) {
                Map tokenData = objectMapper.readValue(file, Map.class);
                ACCESS_TOKEN = (String) tokenData.get("access_token");
                last_auth_time = ((Number) tokenData.get("last_auth_time")).longValue();
                System.out.println("Loaded ACCESS_TOKEN from file: " + ACCESS_TOKEN);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("토큰을 파일에서 로드하는 동안 오류가 발생했습니다", e);
        }
    }
}
