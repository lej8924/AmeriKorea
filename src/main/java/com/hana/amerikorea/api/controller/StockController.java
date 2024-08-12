package com.hana.amerikorea.api.controller;

import com.hana.amerikorea.api.service.CsvService;
import com.hana.amerikorea.api.service.StockProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StockController {

    @Autowired
    private CsvService csvService;

    @Autowired
    private StockProcessor stockProcessor;

    @GetMapping("/getCurrentPrice")
    public double getCurrentPrice(@RequestParam String stockName, @RequestParam boolean isKorean) {
        String stockCode = csvService.getStockCode(stockName, isKorean);
        if(stockCode.equals("종목코드를 못 찾았습니다")) {
            throw new RuntimeException("Stock code not found for the given stock name");
        }
        return stockProcessor.getCurrentPrice(stockCode);
    }

    @GetMapping("/getPurchasePrice")
    public double getPurchasePrice(
            @RequestParam String stockName,
            @RequestParam boolean isKorean,
            @RequestParam String purchaseDate
    ) {
        String stockCode = csvService.getStockCode(stockName, isKorean);
        if(stockCode.equals("종목코드를 못 찾았습니다")) {
            throw new RuntimeException("Stock code not found for the given stock name");
        }
        return stockProcessor.getPurchasePrice(stockCode, purchaseDate);
    }
    @GetMapping("/getCasHDividend")
    public double getCashDividend(
            @RequestParam String stockName,
            @RequestParam boolean isKorean
    ) {
        String stockCode = csvService.getStockCode(stockName, isKorean);
        if(stockCode.equals("종목코드를 못 찾았습니다")) {
            throw new RuntimeException("Stock code not found for the given stock name");
        }
        return stockProcessor.getCashDividend(stockCode);
    }

    @GetMapping("/calculateInvestmentDividendRate") //투자배당률
    public double calculateInvestmentDividendRate(
            @RequestParam String stockName,
            @RequestParam boolean isKorean,
            @RequestParam String purchaseDate
    ) {
        String stockCode = csvService.getStockCode(stockName, isKorean);
        Map<String, Double> stockData = new HashMap<>();
        stockData.put("cashDividend", stockProcessor.getCashDividend(stockCode));
        stockData.put("purchasePrice", stockProcessor.getPurchasePrice(stockCode, purchaseDate));
        return stockProcessor.calculateInvestmentDividendRate(stockData);
    }

    @GetMapping("/calculateMarketDividendRate")
    public double calculateMarketDividendRate(
            @RequestParam String stockName,
            @RequestParam boolean isKorean
    ) {
        String stockCode = csvService.getStockCode(stockName, isKorean);
        Map<String, Double> stockData = new HashMap<>();
        stockData.put("cashDividend", stockProcessor.getCashDividend(stockCode));
        stockData.put("currentPrice", stockProcessor.getCurrentPrice(stockCode));
        return stockProcessor.calculateMarketDividenRate(stockData);
    }

    @GetMapping("/calculateProfit")
    public double calculateProfit(
            @RequestParam String stockName,
            @RequestParam boolean isKorean,
            @RequestParam int amount,
            @RequestParam String purchaseDate
    ){
        String stockCode = csvService.getStockCode(stockName, isKorean);
        Map<String, Double> stockData = new HashMap<>();
        stockData.put("currentPrice", stockProcessor.getCurrentPrice(stockCode));
        stockData.put("purchasePrice", stockProcessor.getPurchasePrice(stockCode, purchaseDate));
        return stockProcessor.calculateProfit(stockData, amount);
    }

}
