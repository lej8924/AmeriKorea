package com.hana.amerikorea.api.service;

import com.hana.amerikorea.chart.dto.response.ChartResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockProcessor {

    private static final String JSON_DATA = "src/main/resources/dividends_2023.json";

    private JsonParser jsonParser;
    private ChartJsonParser chartJsonParser;

    @Autowired
    private ApiService apiService;

    @Autowired
    private StockProcessor(JsonParser jsonParser, ChartJsonParser chartJsonParser) {
        this.jsonParser = jsonParser;
        this.chartJsonParser = chartJsonParser;
    }

    private static final String PURCHASE_PRICE_API_KEY = "inquire-daily-itemchartprice";
    private static final String CURRENT_PRICE_API_KEY = "inquire-price";
    private static final String CASH_DIVIDEND_API_KEY = "dividend";
    private static final String OVERSEA_PRICE_API_KEY = "oversea_dailyprice";


    public List<ChartResponse.ChartData> getKoreanStockChartData(String StockCode) {
        String[] formattedDates = getFormattedTodayAndOneMonthBefore();
        String formattedToday = formattedDates[0];
        String formattedOneMonthBefore = formattedDates[1];

        Map<String, String> params = new HashMap<>();
        params.put("fid_input_date_1", formattedOneMonthBefore);
        params.put("fid_input_date_2", formattedToday);
        params.put("fid_input_iscd", StockCode);

        String response = apiService.callApi("inquire-daily-itemchartprice", params).block();

        System.out.println(response);

        return chartJsonParser.extractChartData(response);
    }

    public List<ChartResponse.ChartData> getOverseaStockChartData(String StockCode) {
        String[] formattedDates = getFormattedTodayAndOneMonthBefore();
        String formattedToday = formattedDates[0];
        //String formattedOneMonthLater = formattedDates[1];

        Map<String, String> params = new HashMap<>();
        params.put("GUBN", "2");
        params.put("BYMD", formattedToday);
        params.put("SYMB", StockCode);

        String response = apiService.callApi("oversea_dailyprice", params).block();

        return chartJsonParser.extractCartData_oversea(response);
    }

    public double getCashDividendMonth_oversea(String stockCode) {
        String jsonData = readJsonFromFile(JSON_DATA);
        return jsonParser.extractDividendMonth_oversea(jsonData, stockCode);
    }

    public double getCashDividend_oversea(String stockCode) {
        String jsonData = readJsonFromFile(JSON_DATA);
        return jsonParser.extractDividend_oversea(jsonData, stockCode);
    }

    public double getCurrentPrice_oversea(String stockCode) {
        Map<String, String> params = new HashMap<>();
        params.put("SYMB", stockCode);
        return callApiAndExtractDouble(OVERSEA_PRICE_API_KEY, "clos", params);
    }

    public double getPurchasePrice_oversea(String stockCode, String purchaseDate) {
        Map<String, String> params = new HashMap<>();
        params.put("SYMB", stockCode);
        params.put("BYMD", purchaseDate);
        return callApiAndExtractDouble(OVERSEA_PRICE_API_KEY, "clos", params);
    }

    public double getCurrentPrice(String stockCode) {
        Map<String, String> params = new HashMap<>();
        params.put("fid_cond_mrkt_div_code", "J");
        params.put("fid_input_iscd", stockCode);
        return callApiAndExtractDouble(CURRENT_PRICE_API_KEY, "stck_prpr", params);
    }

    public double getPurchasePrice(String stockCode, String purchaseDate) {
        Map<String, String> params = new HashMap<>();
        params.put("fid_cond_mrkt_div_code", "J");
        params.put("fid_input_iscd", stockCode);
        params.put("fid_input_date_1", purchaseDate);
        params.put("fid_input_date_2", purchaseDate);
        return callApiAndExtractDouble(PURCHASE_PRICE_API_KEY, "stck_oprc", params);
    }

    public double getCashDividend(String stockCode) {
        Map<String, String> params = new HashMap<>();
        params.put("f_dt", "2022");
        params.put("t_dt", "2030");
        params.put("sht_cd", stockCode);
        return callApiAndExtractDouble(CASH_DIVIDEND_API_KEY, "per_sto_divi_amt", params);
    }

    public double getCashDividendMonth(String stockCode) {
        Map<String, String> params = new HashMap<>();
        params.put("f_dt", "2022");
        params.put("t_dt", "2030");
        params.put("sht_cd", stockCode);
        return callApiAndExtractDouble(CASH_DIVIDEND_API_KEY, "record_date", params);
    }

    private double callApiAndExtractDouble(String apiKey, String fieldName, Map<String, String> additionalParams) {
        Mono<String> responseMono = apiService.callApi(apiKey, additionalParams);
        String response = responseMono.block();
        System.out.println(response);

        if (apiKey.equals(PURCHASE_PRICE_API_KEY)) {
            return jsonParser.extractPurchasePrice(response, fieldName);
        } else if (apiKey.equals(CURRENT_PRICE_API_KEY)) {
            return jsonParser.extractCurrentPrice(response, fieldName);
        } else if (apiKey.equals(CASH_DIVIDEND_API_KEY)) {
            return jsonParser.extractDividend(response, fieldName);
        } else if (apiKey.equals(OVERSEA_PRICE_API_KEY)) {
            return jsonParser.extractPrice_oversea(response, fieldName);
        } else {
            throw new RuntimeException("적용되는 API가 아닙니다");
        }
    }

    private String readJsonFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.out.println("Failed to read JSON File");
            return "";
        }
    }

    private String[] getFormattedTodayAndOneMonthBefore() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyyMMdd");

        LocalDate today = LocalDate.now();

        LocalDate oneMonthBefore = today.minusMonths(1);

        String formattedToday = today.format(formatter);
        String formattedOneMonthBefore = oneMonthBefore.format(formatter);

        return new String[]{formattedToday, formattedOneMonthBefore};
    }
}

