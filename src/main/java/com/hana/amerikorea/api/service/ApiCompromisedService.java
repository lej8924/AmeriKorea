package com.hana.amerikorea.api.service;

import java.util.function.Supplier;

import com.hana.amerikorea.api.model.StockData;
import com.hana.amerikorea.asset.dto.AssetDTO;
import com.hana.amerikorea.portfolio.domain.type.DividendFrequency;
import com.hana.amerikorea.portfolio.domain.type.Sector;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public AssetDTO createAssetDTO(String tickerSymbol, String stockName, int quantity, String purchaseDate, boolean isKorean)
            throws IOException, ExecutionException, InterruptedException {
        StockData stockData = csvService.getStockData(stockName, isKorean);
        if(stockData==null) {
            throw new IllegalArgumentException("Stock code could not be founded");
        }

        String stockCode = stockData.getSymbol();

        Supplier<Double> currentPriceSupplier = isKorean
                ? () -> retry(() -> stockProcessor.getCurrentPrice(stockCode), 3)
                : () -> retry(() -> stockProcessor.getCurrentPrice_oversea(stockCode), 3);

        Supplier<Double> purchasePriceSupplier = isKorean
                ? () -> retry(() -> stockProcessor.getPurchasePrice(stockCode, purchaseDate), 3)
                : () -> retry(() -> stockProcessor.getPurchasePrice_oversea(stockCode, purchaseDate), 3);

        Supplier<Double> cashDividendFutureSupplier = isKorean
                ? () -> retry(() -> stockProcessor.getCashDividend(stockCode), 3)
                : () -> retry(() -> stockProcessor.getCashDividend_oversea(stockCode), 3);

        Supplier<Double> dividendMonthSupplier = isKorean
                ? () -> retry(() -> stockProcessor.getCashDividendMonth(stockCode), 3)
                : () -> retry(() -> stockProcessor.getCashDividendMonth_oversea(stockCode), 3);

        CompletableFuture<Double> currentPriceFuture = CompletableFuture.supplyAsync(currentPriceSupplier);

        CompletableFuture<Double> purchasePriceFuture = CompletableFuture.supplyAsync(purchasePriceSupplier);

        CompletableFuture<Double> cashDividendFuture = CompletableFuture.supplyAsync(() -> {
            delayExecution(2000);  // 2000ms delay
            return cashDividendFutureSupplier.get();
        });

        CompletableFuture<Double> dividendMonthFuture = CompletableFuture.supplyAsync(dividendMonthSupplier);

        CompletableFuture.allOf(currentPriceFuture, purchasePriceFuture, cashDividendFuture, dividendMonthFuture).join();

        Double currentPrice = currentPriceFuture.get();
        Double purchasePrice = purchasePriceFuture.get();
        Double cashDividend = cashDividendFuture.get();
        Double dividendMonth = dividendMonthFuture.get();

        Double profit = (currentPrice - purchasePrice) * quantity;
        Double dividendYield = (cashDividend / currentPrice) * 100; // 무슨 정보??
        Double investmentDividendYield = (cashDividend / purchasePrice) * 100; // 투자 배당 수익률

        String country = isKorean ? "한국" : "미국";

        AssetDTO assetDTO = AssetDTO.builder()
                .tickerSymbol(stockData.getSymbol())
                .stockName(stockData.getName())
                .sector(Sector.valueOf(stockData.getSector().toUpperCase())) //Sector.java사용으로 수정
                .industry(stockData.getIndustry())
                .quantity(quantity)
                .country(country)
                .purchasePrice(purchasePrice)
                .currentPrice(currentPrice)
                .dividendMonth(dividendMonth.toString())
                .investmentDividendYield(investmentDividendYield)
                .profit(profit)
                .dividendFrequency(DividendFrequency.QUARTERLY)
                .dividendPerShare(cashDividend)
                .build();

        assetDTO.setAssetValue(currentPrice * quantity);

        return assetDTO;
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
}

