package com.hana.amerikorea.portfolio.domain;

import com.hana.amerikorea.portfolio.domain.type.DividendFrequency;
import com.hana.amerikorea.portfolio.domain.type.Sector;
import com.hana.amerikorea.asset.dto.response.AssetResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name="asset")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Asset {


    @Id
    @Column(name = "ticker_symbol", nullable = false, unique = true)
    private String tickerSymbol; // 주식 심볼

    @Column(name = "stock_name", nullable = false)
    private String stockName; // 주식 이름

    @Enumerated(EnumType.STRING)
    @Column(name = "sector")
    private Sector sector; // 섹터

    @Column(name = "industry")
    private String industry; // 산업

    @Column(name = "country")
    private String country; // 국가

    @Column(name = "quantity", nullable = false)
    private int quantity; // 보유 수량

    @Column(name = "purchase_price", nullable = false)
    private double purchasePrice; // 구매 가격

    @Column(name = "purchase_date", nullable = false)
    private String purchaseDate; // 구매 날짜

    @Column(name = "annual_dividend", nullable = false)
    private double annualDividend; // 연간배당금

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dividend> dividends = new ArrayList<>();


    public Asset(
            String tickerSymbol,
            String stockName,
            Sector sector,
            String industry,
            String country,
            int quantity,
            String purchaseDate,
            double purchasePrice,
            double annualDividend) {

        this.tickerSymbol = tickerSymbol;
        this.stockName = stockName;
        this.sector = sector;
        this.industry = industry;
        this.country = country;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.annualDividend = annualDividend;
    }

    ////////////////// 데이터 추가를 위한 임시 함수//////////////////////////
    public Asset(String stockName, int quantity, String purchaseDate) {
        this.stockName = stockName;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;

        this.purchasePrice = 1000;
        this.annualDividend = 100.0;
        this.tickerSymbol = generateTickerSymbol(stockName); // Assuming you have a method to generate a ticker symbol
        this.sector = Sector.IT; // Replace with an appropriate default sector or value
        this.industry = "Default Industry"; // Replace with an appropriate default industry
        this.country = "Default Country"; // Replace with an appropriate default country

    }
    // Example method to generate a ticker symbol from the stock name
    private String generateTickerSymbol(String stockName) {
        return stockName.substring(0, Math.min(stockName.length(), 4)).toUpperCase(); // Simplistic approach
    }
    //////////////////// 나중에 지울 함수/////////////////////////

    public AssetResponse toDto() {
        return AssetResponse.builder()
                .stockName(this.stockName)
                .purchaseDate(this.purchaseDate)
                .quantity(this.quantity)
                .country(this.country)
                .tickerSymbol(this.tickerSymbol)
                .sector(this.sector)
                .industry(this.industry)
                .purchasePrice(this.purchasePrice)
                .annualDividend(this.annualDividend)
                .assetValue(this.purchasePrice * this.quantity)  // 계산된 자산 가치
                .profit((this.purchasePrice * this.quantity) - this.purchasePrice)  // 계산된 수익
                .investmentDividendYield((this.annualDividend / this.purchasePrice) * 100)  // 투자 배당 수익률 계산
                .currentPrice(this.purchasePrice)  // 현재 가격은 임의로 설정
                .marketDividendYield((this.annualDividend / this.purchasePrice) * 100)  // 시가 배당 수익률 계산
                .build();
    }

}
