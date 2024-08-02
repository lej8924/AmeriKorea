package com.hana.amerikorea.portfolio.domain;

import com.hana.amerikorea.portfolio.domain.type.Sector;
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
    private String tickerSymbol;

    @Column(name="stock_name", nullable = false)
    private String stockName;

    @Enumerated(EnumType.STRING)
    @Column(name="sector")
    private Sector sector;

    @Column(name="industry")
    private String industry;

    @Column(name="exchange")
    private String exchange;

    @Column(name="country")
    private String country;

    public Stock(String tickerSymbol, String stockName, Sector sector, String industry, String exchange, String country) {
        this.tickerSymbol = tickerSymbol;
        this.stockName = stockName;
        this.sector = sector;
        this.industry = industry;
        this.exchange = exchange;
        this.country = country;
    }
}
