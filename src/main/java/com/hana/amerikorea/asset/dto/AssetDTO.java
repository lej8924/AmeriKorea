package com.hana.amerikorea.asset.dto;

import com.hana.amerikorea.asset.domain.type.DividendFrequency;
import com.hana.amerikorea.asset.domain.type.Sector;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class AssetDTO {

    //사용자 입력 데이터///////////////////////////////////////////////////

    private int quantity; // 보유 수량
    private String stockName; // 주식 이름
    private String purchaseDate; // 구매 날짜
    private boolean isKorea; // 한국/미국 주식인지?

    //계산 가능 데이터///////////////////////////////////////////////////////
    private String tickerSymbol; // 주식 심볼
    private String industry; // 산업
    private String exchange; // 거래소
    private String country; // 국가
    private Sector sector; // 섹터
    private double assetValue; // 자산 가치(현재 가격 * 수량)
    private double profit; // 수익(현재 가격 - 구매 가격)
    private double currentPrice; // 현재 주식 가격
    private double purchasePrice; // 구매 가격

    //배당 관련 데이터//////////////////////////////////////////
    private String dividendMonth; // 배당 월
    private double investmentDividendYield; // 투자 배당 수익률
    private double dividendPerShare; // 주당 배당금
    private DividendFrequency dividendFrequency; // 배당 주기


    @Builder
    public AssetDTO(String tickerSymbol, String stockName, Sector sector, String industry, String exchange, String country, int quantity, double assetValue, double purchasePrice, double profit, double currentPrice, String dividendMonth, double investmentDividendYield, double dividendPerShare, DividendFrequency dividendFrequency) {
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

    public AssetDTO(String tickerSymbol, String stockName, int quantity, double purchasePrice, double currentPrice) {
        this.tickerSymbol = tickerSymbol;
        this.stockName = stockName;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.currentPrice = currentPrice;
    }

    public long changeDoubleToLong(double value) {
        return (long)value;
    }

}
