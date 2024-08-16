package com.hana.amerikorea.portfolio.domain;

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
    @JoinColumn(name = "ticker_symbol", nullable = false)
    private Asset asset;  // `Asset`과의 연관 관계 (외래 키)

    public Dividend(LocalDate dividendDate, double dividendAmount, Asset asset) {
        this.dividendDate = dividendDate;
        this.dividendAmount = dividendAmount;
        this.asset = asset;
    }
}
