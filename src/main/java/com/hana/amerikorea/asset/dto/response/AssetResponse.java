package com.hana.amerikorea.asset.dto.response;

import com.hana.amerikorea.portfolio.domain.type.Sector;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class AssetResponse{
    private String stockName;
    private String purchaseDate;
    private int quantity;
    private String country;
    private String tickerSymbol;
    private Sector sector;
    private String industry;
    private double purchasePrice;
    private double annualDividend;
    ////////////////////////
    private double assetValue; //자산 가치
    private double profit; // 수익
    private double investmentDividendYield; //투자 수익률
    private double currentPrice; //현재 가격
    private double marketDividendYield; //시가 배당률
    ///////////////////////////
    private Map<LocalDate,Double> dividends; // 배당월,배당금
    private List<Integer> dividendMonths; // 배당월 리스트

    public void setDividends(Map<LocalDate, Double> dividends) {
        this.dividends = dividends;
        this.dividendMonths = dividends.keySet().stream()
                .map(LocalDate::getMonthValue) // 월만 추출
                .distinct() // 중복 제거
                .collect(Collectors.toList()); // 리스트로 변환
    }

    @Builder
    public AssetResponse(String stockName, String purchaseDate, int quantity, String country, String tickerSymbol, Sector sector, String industry, double purchasePrice, double annualDividend, double assetValue, double profit, double investmentDividendYield, double currentPrice, double marketDividendYield,Map<LocalDate,Double> dividends) {
        this.stockName = stockName;
        this.purchaseDate = purchaseDate;
        this.quantity = quantity;
        this.country = country;
        this.tickerSymbol = tickerSymbol;
        this.sector = sector;
        this.industry = industry;
        this.purchasePrice = purchasePrice;
        this.annualDividend = annualDividend;
        this.assetValue = assetValue;
        this.profit = profit;
        this.investmentDividendYield = investmentDividendYield;
        this.currentPrice = currentPrice;
        this.marketDividendYield = marketDividendYield;
        this.dividends = dividends;
    }


}
