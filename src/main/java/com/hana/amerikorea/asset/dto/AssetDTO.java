package com.hana.amerikorea.asset.dto;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "assetNo")
@NoArgsConstructor
public class AssetDTO {

    private int assetNo;
    private String assetName;
    private int assetAmount;
    private int assetBuy;
    private int currentPrice; // 현재가
    private int evalAmount; // 평가금액
    private int revenue; // 수익
    private int dividendMonth; // 배당월
    private int dividendYield; // 배당률

    @Builder
    public AssetDTO(int assetNo, String assetName, int assetAmount, int assetBuy, int currentPrice) {
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

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
        this.evalAmount = currentPrice * assetAmount;
        this.revenue = (currentPrice - assetBuy) * assetAmount;
    }

}
