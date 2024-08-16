package com.hana.amerikorea.asset.domain;

import com.hana.amerikorea.asset.domain.type.Sector;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "stock_info")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockInfo {

    @Id
    @Column(name = "stock_name", nullable = false, unique = true)
    private String stockName; // 주식 이름

    @Column(name = "ticker_symbol", nullable = false, unique = true)
    private String tickerSymbol; // 주식 심볼

    @Enumerated(EnumType.STRING)
    @Column(name = "sector")
    private Sector sector; // 섹터

    @Column(name = "industry")
    private String industry; // 산업

    public StockInfo(String stockName, String tickerSymbol, Sector sector, String industry) {
        this.stockName = stockName;
        this.tickerSymbol = tickerSymbol;
        this.sector = sector;
        this.industry = industry;
    }
}
