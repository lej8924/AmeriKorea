package com.hana.amerikorea.portfolio.domain;

import com.hana.amerikorea.portfolio.domain.type.DividendFrequency;
import com.hana.amerikorea.portfolio.domain.type.Sector;
import com.hana.amerikorea.portfolio.dto.response.StockResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="stock")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Asset {

    @Id
    @Column(name="ticker_symbol", nullable = false, unique = true)
    private String tickerSymbol; // 주식 심볼 (변하지 않는 값)

    @Column(name="stock_name", nullable = false)
    private String stockName; // 주식 이름 (변하지 않는 값)

    @Enumerated(EnumType.STRING)
    @Column(name="sector")
    private Sector sector; // 섹터 (변하지 않는 값)

    @Column(name="industry")
    private String industry; // 산업 (변하지 않는 값)

    @Column(name="exchange")
    private String exchange; // 거래소 (변하지 않는 값)

    @Column(name="country")
    private String country; // 국가 (변하지 않는 값)

    @Column(name="quantity", nullable = false)
    private int quantity; // 보유 수량 (변하는 값)

    @Column(name="asset_value", nullable = false)
    private double assetValue; // 자산 가치 (변하는 값)

    @Column(name="purchase_price", nullable = false)
    private double purchasePrice; // 구매 가격 (변하는 값)

    @Column(name="profit", nullable = false)
    private double profit; // 수익 (변하는 값)

    @Column(name="current_price", nullable = false)
    private double currentPrice; // 현재 가격 (변하는 값)

    @Column(name="dividend_month")
    private String dividendMonth; // 배당 월 (변하지 않는 값)

    @Column(name="investment_dividend_yield", nullable = false)
    private double investmentDividendYield; // 투자 배당 수익률 (변하는 값)

    @Column(name="dividend_per_share", nullable = false)
    private double dividendPerShare; // 주당 배당금

    @Enumerated(EnumType.STRING)
    @Column(name="dividend_frequency", nullable = false)
    private DividendFrequency dividendFrequency; // 배당 주기



    public Asset(String tickerSymbol, String stockName, Sector sector, String industry, String exchange, String country, int quantity, double assetValue, double purchasePrice, double profit, double currentPrice, String dividendMonth, double investmentDividendYield, double dividendPerShare, DividendFrequency dividendFrequency) {
        this.tickerSymbol = tickerSymbol;
        this.stockName = stockName;
        this.sector = sector;
        this.industry = industry;
        this.exchange = exchange;
        this.country = country;
        this.quantity = quantity;
        this.assetValue = assetValue;
        this.purchasePrice = purchasePrice;
        this.profit = profit;
        this.currentPrice = currentPrice;
        this.dividendMonth = dividendMonth;
        this.investmentDividendYield = investmentDividendYield;
        this.dividendPerShare = dividendPerShare;
        this.dividendFrequency = dividendFrequency;
    }

    ////////////////// 데이터 추가를 위한 임시 함수//////////////////////////
    public Asset(String stockName, int quantity, double purchasePrice) {
        this.stockName = stockName;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;

        // Initialize other fields with default or predetermined values
        this.tickerSymbol = generateTickerSymbol(stockName); // Assuming you have a method to generate a ticker symbol
        this.sector = Sector.IT; // Replace with an appropriate default sector or value
        this.industry = "Default Industry"; // Replace with an appropriate default industry
        this.exchange = "Default Exchange"; // Replace with an appropriate default exchange
        this.country = "Default Country"; // Replace with an appropriate default country
        this.assetValue = quantity * purchasePrice; // Initial asset value based on purchase
        this.profit = 0.0; // Initially, profit is zero
        this.currentPrice = purchasePrice; // Assume current price is the purchase price at creation
        this.dividendMonth = "N/A"; // Or another default value if dividends are not applicable
        this.investmentDividendYield = 0.0; // Initial dividend yield is zero
        this.dividendPerShare = 0.0; // Initially, dividend per share is zero
        this.dividendFrequency = DividendFrequency.MONTHLY; // Replace with appropriate default frequency
    }
    // Example method to generate a ticker symbol from the stock name
    private String generateTickerSymbol(String stockName) {
        return stockName.substring(0, Math.min(stockName.length(), 4)).toUpperCase(); // Simplistic approach
    }
    //////////////////// 나중에 지울 함수/////////////////////////


    public StockResponse toDto() {
        return new StockResponse(
                this.tickerSymbol,
                this.stockName,
                this.sector,
                this.industry,
                this.exchange,
                this.country,
                this.quantity,
                this.assetValue,
                this.purchasePrice,
                this.profit,
                this.currentPrice,
                this.dividendMonth,
                this.investmentDividendYield
        );
    }

    public double getMonthlyDividend() {
        switch (dividendFrequency) {
            case MONTHLY:
                return dividendPerShare * quantity;
            case QUARTERLY:
                return (dividendPerShare * quantity) / 3;
            case SEMIANNUALLY:
                return (dividendPerShare * quantity) / 6;
            case ANNUALLY:
                return (dividendPerShare * quantity) / 12;
            default:
                return 0;
        }
    }
}
