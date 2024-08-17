package com.hana.amerikorea.asset.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "dividend")
@Getter
@Setter
@NoArgsConstructor
public class Dividend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 기본 키

    @Column(name = "dividend_date", nullable = false)
    private LocalDate dividendDate;  // 배당 날짜

    @Column(name = "dividend_amount", nullable = false)
    private double dividendAmount;  // 배당금

    @ManyToOne
    @JoinColumn(name="ticker_symbol", referencedColumnName = "ticker_symbol",nullable = false)
    private StockInfo stockInfo;


    public Dividend(LocalDate dividendDate, double dividendAmount, StockInfo stockInfo) {
        this.dividendDate = dividendDate;
        this.dividendAmount = dividendAmount;
        this.stockInfo = stockInfo;

    }
}
