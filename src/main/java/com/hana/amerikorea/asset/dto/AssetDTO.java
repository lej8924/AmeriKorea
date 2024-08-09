package com.hana.amerikorea.asset.dto;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "assetID")
@NoArgsConstructor
public class AssetDTO {

    private long assetID; // data ID
    private String assetName; // 주식명
    private long assetAmount; // 수량
    private long assetBuy; // 구매 가격
    private long currentPrice; // 현재가
    private long valuation; // 평가금액
    private long revenue; // 수익
    private double revenuePercent; // 수익률
    private int dividendMonth; // 배당월
    private double dividendYield; // 배당률

    @Builder
    public AssetDTO(long assetID, String assetName, long assetAmount, long assetBuy, long currentPrice) {
        this.assetID = assetID;
        this.assetName = assetName;
        this.assetAmount = assetAmount;
        this.assetBuy = assetBuy;
        this.currentPrice = currentPrice;
        this.valuation = currentPrice * assetAmount;
        this.revenue = (currentPrice - assetBuy) * assetAmount;
        this.revenuePercent = ((double) (currentPrice - assetBuy) / assetBuy) * 100;
        this.dividendMonth = 0;
        this.dividendYield = 0;
    }

    public void setCurrentPrice(long currentPrice) {
        this.currentPrice = currentPrice;
        this.valuation = currentPrice * assetAmount;
        this.revenue = (currentPrice - assetBuy) * assetAmount;
    }

}
