package com.hana.amerikorea.portfolio.domain;

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
public class Stock {

    @Id
    @Column(name="ticker_symbol", nullable = false, unique = true)
    private String tickerSymbol; // 변하지 않는 값

    @Column(name="stock_name", nullable = false)
    private String stockName; // 변하지 않는 값

    @Enumerated(EnumType.STRING)
    @Column(name="sector")
    private Sector sector; // 변하지 않는 값

    @Column(name="industry")
    private String industry; // 변하지 않는 값

    @Column(name="exchange")
    private String exchange; // 변하지 않는 값

    @Column(name="country")
    private String country; // 변하지 않는 값

    @Column(name="quantity", nullable = false)
    private int quantity; // 변하는 값

    @Column(name="asset_value", nullable = false)
    private double assetValue; // 변하는 값

    @Column(name="purchase_price", nullable = false)
    private double purchasePrice; // 변하는 값

    @Column(name="profit", nullable = false)
    private double profit; // 변하는 값

    @Column(name="current_price", nullable = false)
    private double currentPrice; // 변하는 값

    @Column(name="dividend_month")
    private String dividendMonth; // 변하지 않는 값

    @Column(name="investment_dividend_yield", nullable = false)
    private double investmentDividendYield; // 변하는 값

    public Stock(String tickerSymbol, String stockName, Sector sector, String industry, String exchange, String country, int quantity, double assetValue, double purchasePrice, double profit, double currentPrice, String dividendMonth, double investmentDividendYield) {
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
    }

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
}
