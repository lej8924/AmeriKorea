package com.hana.amerikorea.api.config;

import com.hana.amerikorea.api.model.ApiInfo;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApiConfig {
    public static final String REST_BASE_URL="https://openapi.koreainvestment.com:9443";
    public static final String WS_BASE_URL="ws://ops.koreainvestment.com:9443";
    public static final String APPKEY = "PSA60aQNb0Bb4QC28GYKzwvzg09Grn4p6R43";  //유출되면 안됩니다!!!
    public static final String APPSECRET = "EZw6CsYLPGUpPoO/AZMamCoZIByDzkBoQmn0gS6uD3tw9wz8uDH0JoQEQLGeFPpq448D5+nl8yFJFCvcOGZCrCPrQlhLjU56H37db8DRy5gWA4LUNYiTQHF9wCIpwXzFBguvE4CY4oTRNrEdiVYum8w8deObyObIsnxVH46YXD/EMpiP1fs="; //유출되면 안됩니다!!!

    private final Map<String, ApiInfo> apiInfoMap;

    public ApiConfig() {
        this.apiInfoMap = new HashMap<>();
        InitApiInfo();
    }

    private void InitApiInfo() {
        apiInfoMap.put("inquire-price", new ApiInfo("/uapi/domestic-stock/v1/quotations/inquire-price", "FHKST01010100")); //주식현재가
        apiInfoMap.put("inquire-daily-price", new ApiInfo("/uapi/domestic-stock/v1/quotations/inquire-daily-price", "FHKST01010400")); //국내주식기간시세(일/주/월/년)
        apiInfoMap.put("inquire-daily-itemchartprice", new ApiInfo("/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice", "FHKST03010100")); //주식현재 일자별
        apiInfoMap.put("search-info", new ApiInfo("/uapi/domestic-stock/v1/quotations/search-info", "CTPF1604R")); //상품기본조회
        apiInfoMap.put("search-stock-info", new ApiInfo("/uapi/domestic-stock/v1/quotations/search-stock-info", "CTPF1002R")); //주식기본조회
        apiInfoMap.put("dividend", new ApiInfo("/uapi/domestic-stock/v1/ksdinfo/dividend", "HHKDB669102C0")); //예탁원정보(배당일)
        apiInfoMap.put("oversea_price", new ApiInfo("/uapi/overseas-price/v1/quotations/price", "HHDFS00000300")); //해외주식 현제체결가
        apiInfoMap.put("oversea_dailyprice", new ApiInfo("/uapi/overseas-price/v1/quotations/dailyprice", "HHDFS76240000")); //주식현재 일자별
        apiInfoMap.put("oversea_search-info", new ApiInfo("/uapi/overseas-price/v1/quotations/search-info", "CTPF1702R")); //주식현재 일자별
    }

    public ApiInfo getApiInfo(String key) {
        return apiInfoMap.get(key);
    }

}

