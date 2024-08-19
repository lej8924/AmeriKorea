package com.hana.amerikorea.portfolio.dto.response;

import com.hana.amerikorea.asset.dto.response.AssetResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record PortfolioSummary(
        List<AssetResponse> stocks,
        double totalAssetValue,
        double totalProfit,
        double investmentDividendYield,
        double marketDividendYield,
        NaverNewsResponse naverNewsResponse,
        List<DividendInfo> dividendInfos
) {
    public double getRoundedInvestmentDividendYield() {
        return roundToThreeDecimalPlaces(investmentDividendYield);
    }

    public double getRoundedMarketDividendYield() {
        return roundToThreeDecimalPlaces(marketDividendYield);
    }

    private double roundToThreeDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(3, RoundingMode.HALF_UP); // 소수점 셋째 자리로 반올림
        return bd.doubleValue();
    }

}
