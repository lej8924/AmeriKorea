package com.hana.amerikorea.api.service;

import com.hana.amerikorea.asset.dto.AssetDTO;
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

    public AssetDTO createAssetDTO(long assetID, String assetName, long assetAmount, String stockName, String purchaseDate, boolean isKorean) throws IOException, ExecutionException, InterruptedException {
        String stockCode = csvService.getStockCode(stockName, isKorean);
        if(stockCode.equals("종목코드를 못 찾았습니다")) {
            throw new IllegalArgumentException("Stock code could not be founded");
        }


        CompletableFuture<Double> currentPriceFuture = CompletableFuture.supplyAsync(() -> {
            return stockProcessor.getCurrentPrice(stockCode);
        });

        CompletableFuture<Double> purchasePriceFuture = CompletableFuture.supplyAsync(() -> {
            return stockProcessor.getPurchasePrice(stockCode, purchaseDate);
        });

        CompletableFuture<Double> cashDividendFuture = CompletableFuture.supplyAsync(() -> {
            delayExecution(2000);  // 500ms delay
            return stockProcessor.getCashDividend(stockCode);
        });

        CompletableFuture<Double> dividendMonthFuture = CompletableFuture.supplyAsync(() -> {
            return stockProcessor.getCashDividendMonth(stockCode);
        });


        CompletableFuture.allOf(currentPriceFuture, purchasePriceFuture, cashDividendFuture, dividendMonthFuture).join();

        Double currentPrice = currentPriceFuture.get();
        Double purchasePrice = purchasePriceFuture.get();
        Double cashDividend = cashDividendFuture.get();
        Double dividendMonth = dividendMonthFuture.get();

        Double revenue = (currentPrice - purchasePrice) * assetAmount;
        Double dividendYield = (cashDividend / currentPrice) * 100;
        Double investmentDividendYield = (cashDividend / purchasePrice) * 100;

        AssetDTO assetDTO = AssetDTO.builder()
                .assetID(assetID)
                .assetName(assetName)
                .assetAmount(assetAmount)
                .assetBuy(purchasePrice.longValue())
                .currentPrice(currentPrice.longValue())
                .build();

        assetDTO.setEvalAmount(currentPrice.longValue() * assetAmount);
        assetDTO.setDividendMonth(dividendMonth.intValue());
        assetDTO.setDividendYield(dividendYield);
        assetDTO.setRevenue(revenue.longValue());

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
}
