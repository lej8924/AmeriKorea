package com.hana.amerikorea.api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

import com.hana.amerikorea.asset.dto.response.AssetResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ApiCompromisedService {

    private final StockProcessor stockProcessor;
    private final CsvService csvService;

    public ApiCompromisedService(StockProcessor stockProcessor, CsvService csvService) {
        this.stockProcessor = stockProcessor;
        this.csvService = csvService;
    }

    public AssetResponse createAssetDTO(String stockName, int quantity, String purchaseDate, boolean isKorean)
            throws ExecutionException, InterruptedException {
        String stockCode = csvService.getStockCode(stockName, isKorean);
        if(stockCode.equals("종목코드를 못 찾았습니다")) {
            throw new IllegalArgumentException("Stock code could not be founded");
        }
        System.out.println("날짜 테스트 출력 : " + purchaseDate);
        String newPurchaseDate = convertDateFormat(purchaseDate);
        System.out.println("새로운 날짜 테스트 출력 : " + newPurchaseDate);

        CompletableFuture<Double> currentPriceFuture = CompletableFuture.supplyAsync(() -> retry(() -> stockProcessor.getCurrentPrice(stockCode), 3));

        CompletableFuture<Double> purchasePriceFuture = CompletableFuture.supplyAsync(() -> retry(() -> stockProcessor.getPurchasePrice(stockCode, newPurchaseDate), 3));

        CompletableFuture<Double> cashDividendFuture = CompletableFuture.supplyAsync(() -> {
            delayExecution(2000);  // 2000ms delay
            return retry(() -> stockProcessor.getCashDividend(stockCode), 3);
        });

        CompletableFuture<Double> dividendMonthFuture = CompletableFuture.supplyAsync(() -> retry(() -> stockProcessor.getCashDividendMonth(stockCode), 3));

        CompletableFuture.allOf(currentPriceFuture, purchasePriceFuture, cashDividendFuture, dividendMonthFuture).join();

        Double currentPrice = currentPriceFuture.get();
        Double purchasePrice = purchasePriceFuture.get();
        Double cashDividend = cashDividendFuture.get();

        Double profit = (currentPrice - purchasePrice) * quantity;
        Double investmentDividendYield = (cashDividend / purchasePrice) * 100; // 투자 배당 수익률

        AssetResponse assetResponse = AssetResponse.builder()
                .tickerSymbol(stockCode)
                .stockName(stockName)
                .quantity(quantity)
                .purchasePrice(purchasePrice)
                .currentPrice(currentPrice)
                .build();

        assetResponse.setAssetValue(currentPrice * quantity);
        assetResponse.setInvestmentDividendYield(investmentDividendYield);
        assetResponse.setProfit(profit.longValue());

        return assetResponse;
    }

    private void delayExecution(long millis) {
        try {
            Thread.sleep(millis);
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private <T> T retry(Supplier<T> supplier, int maxRetries) {
        int attempt = 0;
        while (true) {
            try {
                return supplier.get();
            } catch (Exception e) {
                if (++attempt >= maxRetries) {
                    throw e;
                }
                delayExecution(1000); // Delay between retries
            }
        }
    }

    public static String convertDateFormat(String dateStr) {
        // 입력 형식
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 출력 형식
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 문자열을 LocalDate 객체로 변환
        LocalDate date = LocalDate.parse(dateStr, inputFormatter);

        // 원하는 형식으로 변환하여 반환
        return date.format(outputFormatter);
    }
}
