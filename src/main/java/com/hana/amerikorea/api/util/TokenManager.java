//package com.hana.amerikorea.api.util;
//
//import com.hana.amerikorea.api.config.ApiConfig;
//import com.hana.amerikorea.api.model.OauthInfo;
//import com.hana.amerikorea.api.model.TokenInfo;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@Component
//public class TokenManager {
//    private final WebClient webClient;
//    public static String ACCESS_TOKEN;
//    public static long last_auth_time=0;
//
//    public TokenManager(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl(ApiConfig.REST_BASE_URL).build();
//    }
//
//    public String getAccessToken() {
//        if(ACCESS_TOKEN==null) {
//            ACCESS_TOKEN=generateAccessToken();
//            System.out.println("generated ACCESS_TOKEN: "+ACCESS_TOKEN);
//        }
//        return ACCESS_TOKEN;
//    }
//
//    public String generateAccessToken() {
//        String url = ApiConfig.REST_BASE_URL+"/oauth2/tokenP";
//        OauthInfo bodyOauthInfo = new OauthInfo();
//        bodyOauthInfo.setGrant_type("client_credentials");
//        bodyOauthInfo.setAppkey(ApiConfig.APPKEY);
//        bodyOauthInfo.setAppsecret(ApiConfig.APPSECRET);
//
//
//        Mono<TokenInfo> mono = webClient.post()
//                .uri(url)
//                .bodyValue(bodyOauthInfo)
//                .retrieve()
//                .bodyToMono(TokenInfo.class);
//
//        TokenInfo tokenInfo = mono.block();
//        if(tokenInfo==null || tokenInfo.getAccess_token()==null) {
//            throw new RuntimeException("토큰을 가져올 수 없습니다");
//        }
//
//        ACCESS_TOKEN = tokenInfo.getAccess_token();
//        last_auth_time = System.currentTimeMillis();
//
//        return ACCESS_TOKEN;
//    }
//}
