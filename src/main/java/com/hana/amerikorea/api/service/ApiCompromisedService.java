package com.hana.amerikorea.api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;


import com.hana.amerikorea.api.model.StockData;
import com.hana.amerikorea.asset.domain.type.Sector;
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

    public AssetResponse createAssetDTO(String stockName, int quantity, Double purchasePrice, boolean isKorean)
            throws ExecutionException, InterruptedException {
        StockData stockCode = csvService.getStockData(stockName, isKorean);
        if(stockCode==null) {
            throw new IllegalArgumentException("Stock code could not be founded");
        }

        String stock = stockCode.getSymbol();
//        String newPurchaseDate = convertDateFormat(purchaseDate);

        Supplier<Double> currentPriceSupplier = isKorean
                ? () -> retry(() -> stockProcessor.getCurrentPrice(stock), 3)
                : () -> retry(() -> stockProcessor.getCurrentPrice_oversea(stock), 3);

//        Supplier<Double> purchasePriceSupplier = isKorean
//                ? () -> retry(() -> stockProcessor.getPurchasePrice(stock, newPurchaseDate), 3)
//                : () -> retry(() -> stockProcessor.getPurchasePrice_oversea(stock, newPurchaseDate), 3);

        Supplier<Double> cashDividendFutureSupplier = isKorean
                ? () -> retry(() -> stockProcessor.getCashDividend(stock), 3)
                : () -> retry(() -> stockProcessor.getCashDividend_oversea(stock), 3);

        Supplier<Double> dividendMonthSupplier = isKorean
                ? () -> retry(() -> stockProcessor.getCashDividendMonth(stock), 3)
                : () -> retry(() -> stockProcessor.getCashDividendMonth_oversea(stock), 3);


        CompletableFuture<Double> currentPriceFuture = CompletableFuture.supplyAsync(currentPriceSupplier);

//        CompletableFuture<Double> purchasePriceFuture = CompletableFuture.supplyAsync(purchasePriceSupplier);

        CompletableFuture<Double> cashDividendFuture = CompletableFuture.supplyAsync(() -> {
            delayExecution(2000);  // 2000ms delay
            return cashDividendFutureSupplier.get();
        });

        CompletableFuture<Double> dividendMonthFuture = CompletableFuture.supplyAsync(dividendMonthSupplier);

        CompletableFuture.allOf(currentPriceFuture, cashDividendFuture, dividendMonthFuture).join();

        Double currentPrice = currentPriceFuture.get();
//        Double purchasePrice = purchasePriceFuture.get();
        Double cashDividend = cashDividendFuture.get();

        Double profit = (currentPrice - purchasePrice) * quantity;
        Double investmentDividendYield = (cashDividend / purchasePrice) * 100; // 투자 배당 수익률


        boolean country = isKorean;

        AssetResponse response = AssetResponse.builder()
                .tickerSymbol(stockCode.getSymbol())
                .stockName(stockCode.getName())
                .sector(Sector.valueOf(stockCode.getSector().toUpperCase())) //Sector.java사용으로 수정
                .industry(stockCode.getIndustry())
                .quantity(quantity)
                .country(country)
                .purchasePrice(purchasePrice)
                .currentPrice(currentPrice)
                .investmentDividendYield(investmentDividendYield)
                .profit(profit)
                .build();

        response.setAssetValue(currentPrice * quantity);

        return response;
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
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(dateStr, inputFormatter);

        return date.format(outputFormatter);
    }
}

