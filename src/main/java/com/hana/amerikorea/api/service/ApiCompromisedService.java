package com.hana.amerikorea.api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


import com.hana.amerikorea.api.model.StockData;
import com.hana.amerikorea.asset.domain.Asset;
import com.hana.amerikorea.asset.domain.Dividend;
import com.hana.amerikorea.asset.domain.StockInfo;
import com.hana.amerikorea.asset.domain.type.Sector;
import com.hana.amerikorea.asset.dto.response.AssetResponse;

import com.hana.amerikorea.asset.repository.DividendRepository;
import com.hana.amerikorea.asset.repository.StockInfoRepository;
import com.hana.amerikorea.chart.dto.response.ChartResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ApiCompromisedService {

    private final StockProcessor stockProcessor;
    private final CsvService csvService;

    private final StockInfoRepository stockInfoRepository;
    private final DividendRepository dividendRepository;

    public ApiCompromisedService(StockProcessor stockProcessor, CsvService csvService, StockInfoRepository stockInfoRepository, DividendRepository dividendRepository) {
        this.stockProcessor = stockProcessor;
        this.csvService = csvService;
        this.stockInfoRepository = stockInfoRepository;
        this.dividendRepository = dividendRepository;

    }

    public AssetResponse createAssetDTO(String stockName, int quantity, Double purchasePrice, boolean isKorea) throws ExecutionException, InterruptedException {

        // StockInfo에서 stockName에 해당하는 정보 조회
        StockInfo stockInfo = stockInfoRepository.findByStockName(stockName)
                .orElseThrow(() -> new IllegalArgumentException("해당 주식 정보가 존재하지 않습니다: " + stockName));

        // Asset 생성
        Asset asset = new Asset(stockInfo, quantity, purchasePrice, 0.0,isKorea); // 연간 배당금은 계산 후 설정

        // Dividend 테이블에서 2023년의 배당 정보 조회
        List<Dividend> dividends = dividendRepository.findDividendsByTickerSymbolAndYear(stockInfo.getTickerSymbol());

        // 2023년도의 배당금 합계 계산
        double annualDividend = dividends.stream()
                .mapToDouble(Dividend::getDividendAmount)
                .sum();

        // 배당 정보 Map으로 변환
        Map<LocalDate, Double> dividendMap = dividends.stream()
                .collect(Collectors.toMap(Dividend::getDividendDate, Dividend::getDividendAmount));

        // 현재 주식 가격 API 호출

        Supplier<Double> currentPriceSupplier = isKorea
                ? () -> retry(() -> stockProcessor.getCurrentPrice(stockInfo.getTickerSymbol()), 3)
                : () -> retry(() -> stockProcessor.getCurrentPrice_oversea(stockInfo.getTickerSymbol()), 3);

        CompletableFuture<Double> currentPriceFuture = CompletableFuture.supplyAsync(currentPriceSupplier);
        Double currentPrice = currentPriceFuture.get();

        // 수익 계산
        Double profit = (currentPrice - purchasePrice) * quantity;

        // 투자 배당 수익률 계산
        Double investmentDividendYield = (annualDividend / purchasePrice) * 100;

        // 시가 배당 수익률 계산
        Double marketDividendYield = (annualDividend / currentPrice) * 100;

        // AssetResponse 객체 생성 및 반환
        return AssetResponse.builder()
                .stockName(stockInfo.getStockName())
                .purchaseDate(null) // 예시로 null로 설정 (필요시 설정)
                .quantity(quantity)
                .country(isKorea)
                .tickerSymbol(stockInfo.getTickerSymbol())
                .sector(stockInfo.getSector())
                .industry(stockInfo.getIndustry())
                .purchasePrice(purchasePrice)
                .annualDividend(annualDividend)  // 연간 배당금 설정
                .assetValue(currentPrice * quantity)
                .profit(profit)
                .investmentDividendYield(investmentDividendYield)
                .currentPrice(currentPrice)
                .marketDividendYield(marketDividendYield)
                .dividends(dividendMap)
                .build();
    }

    public ChartResponse createChartDTO(String stockName, boolean isKorean) throws ExecutionException, InterruptedException {
        StockInfo stockInfo = stockInfoRepository.findByStockName(stockName)
                .orElseThrow(() -> new IllegalArgumentException("해당 주식 정보가 존재하지 않습니다."));

        Supplier<List<ChartResponse.ChartData>> chartDataSupplier = isKorean
                ? () -> retry(() -> stockProcessor.getKoreanStockChartData(stockInfo.getTickerSymbol()), 3)
                : () -> retry(() -> stockProcessor.getOverseaStockChartData(stockInfo.getTickerSymbol()), 3);

        CompletableFuture<List<ChartResponse.ChartData>> chartDataFuture = CompletableFuture.supplyAsync(chartDataSupplier);
        List<ChartResponse.ChartData> chartData = chartDataFuture.get();

        return ChartResponse.builder()
                .stockName(stockInfo.getStockName())
                .tickerSymbol(stockInfo.getTickerSymbol())
                .chartData(chartData)
                .build();
    }


    private void delayExecution(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
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

