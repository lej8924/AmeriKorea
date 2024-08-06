package com.hana.amerikorea.portfolio.dto.response;

import com.hana.amerikorea.portfolio.domain.type.Sector;

public record StockResponse(
        String tickerSymbol,
        String stockName,
        Sector sector,
        String industry,
        String exchange,
        String country,
        int quantity,
        double assetValue,
        double purchasePrice,
        double profit,
        double currentPrice,
        String dividendMonth,
        double investmentDividendYield) {


}
