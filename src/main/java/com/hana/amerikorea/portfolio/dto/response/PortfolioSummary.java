package com.hana.amerikorea.portfolio.dto.response;

import java.util.List;

public record PortfolioSummary(
        List<StockResponse> stocks,
        double totalAssetValue,
        double totalProfit,
        double investmentDividendYield,
        double marketDividendYield,
        NaverNewsResponse naverNewsResponse) {
}
