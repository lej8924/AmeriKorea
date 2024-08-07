package com.hana.amerikorea.asset.dto;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "assetNo")
@NoArgsConstructor
public class AssetDTO {

    private long assetNo; // data ID
    private String assetName; // 주식명
    private long assetAmount; // 수량
    private long assetBuy; // 구매 가격
    private long currentPrice; // 현재가
    private long evalAmount; // 평가금액
    private long revenue; // 수익
    private int dividendMonth; // 배당월
    private double dividendYield; // 배당률

    @Builder
    public AssetDTO(long assetNo, String assetName, long assetAmount, long assetBuy, long currentPrice) {
        this.assetNo = assetNo;
        this.assetName = assetName;
        this.assetAmount = assetAmount;
        this.assetBuy = assetBuy;
        this.currentPrice = currentPrice;
        this.evalAmount = currentPrice * assetAmount;
        this.revenue = (currentPrice - assetBuy) * assetAmount;
        this.dividendMonth = 0;
        this.dividendYield = 0;
    }

    public void setCurrentPrice(long currentPrice) {
        this.currentPrice = currentPrice;
        this.evalAmount = currentPrice * assetAmount;
        this.revenue = (currentPrice - assetBuy) * assetAmount;
    }

}
