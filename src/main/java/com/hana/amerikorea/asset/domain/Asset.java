package com.hana.amerikorea.asset.domain;

import com.hana.amerikorea.asset.domain.type.Sector;
import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.asset.dto.response.AssetResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "asset")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 기본 키

    @Column(name = "quantity", nullable = false)
    private int quantity; // 보유 수량

    @Column(name = "purchase_price", nullable = false)
    private double purchasePrice; // 구매 가격

    @Column(name = "purchase_date", nullable = true)
    private String purchaseDate; // 구매 날짜

    @Column(name = "annual_dividend", nullable = false)
    private double annualDividend; // 연간배당금

    @Column(name="is_korea",nullable = false)
    private boolean isKorea;

    // Member와의 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", nullable = false)
    private Member member;

    // StockInfo와의 일대일 관계 설정 (stock_name을 외래 키로 사용)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_name", referencedColumnName = "stock_name")
    private StockInfo stockInfo;

    public Asset(StockInfo stockInfo, int quantity, double purchasePrice, double annualDividend, boolean isKorea) {
        this.stockInfo = stockInfo;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.annualDividend = annualDividend;
        this.isKorea = isKorea;
    }

    ////////////////// 데이터 추가를 위한 임시 함수//////////////////////////
    public Asset(String stockName, int quantity, Double purchasePrice) {
        this.stockInfo = new StockInfo(stockName, generateTickerSymbol(stockName), Sector.IT, "Default Industry"); // 새로운 StockInfo 객체 생성
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.annualDividend = 100.0;
    }

    // Example method to generate a ticker symbol from the stock name
    private String generateTickerSymbol(String stockName) {
        return stockName.substring(0, Math.min(stockName.length(), 4)).toUpperCase(); // Simplistic approach
    }

    //////////////////// 나중에 지울 함수/////////////////////////

    public AssetResponse toDto() {
        return AssetResponse.builder()
                .stockName(this.stockInfo.getStockName()) // StockInfo에서 가져옴
                .purchaseDate(this.purchaseDate)
                .quantity(this.quantity)
                .country(true) // 예시로 제공, 실제 데이터에 맞게 설정
                .tickerSymbol(this.stockInfo.getTickerSymbol()) // StockInfo에서 가져옴
                .sector(this.stockInfo.getSector()) // StockInfo에서 가져옴
                .industry(this.stockInfo.getIndustry()) // StockInfo에서 가져옴
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
