package com.hana.amerikorea.portfolio.service;

import com.hana.amerikorea.portfolio.dto.response.NaverNewsResponse;
import com.hana.amerikorea.portfolio.dto.response.PortfolioSummary;
import com.hana.amerikorea.asset.dto.response.AssetResponse;
import com.hana.amerikorea.asset.domain.Asset;
import com.hana.amerikorea.asset.repository.AssetRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PortfolioService {

    private AssetRepository assetRepository;
    private final NaverNewsService naverNewsService;

    public PortfolioSummary getPortfolioSummary(String email) {


        List<AssetResponse> stocks = assetRepository.findByMemberEmail(email,Sort.by(Sort.Direction.DESC, "quantity"))
                .stream()
                .map(Asset::toDto)
                .collect(Collectors.toList());

        if (stocks.isEmpty()) {
            // 포트폴리오가 비어 있을 때 처리
            return new PortfolioSummary(stocks, 0.0, 0.0, 0.0, 0.0, null);
        }

        String query = stocks.get(0).getStockName();

        Double totalAssetValue = stocks.stream()
                .mapToDouble(AssetResponse::getAssetValue)
                .sum();

        Double totalProfit = stocks.stream()
                .mapToDouble(AssetResponse::getProfit)
                .sum();

        double totalInvestment = stocks.stream()
                .mapToDouble(stock -> stock.getPurchasePrice() * stock.getQuantity())
                .sum();

        double totalDividend = stocks.stream()
                .mapToDouble(stock -> stock.getInvestmentDividendYield() * stock.getQuantity())
                .sum();

        double investmentDividendYield = (totalDividend / totalInvestment) * 100;

        double totalCurrentValue = stocks.stream()
                .mapToDouble(stock -> stock.getCurrentPrice() * stock.getQuantity())
                .sum();

        double marketDividendYield = (totalDividend / totalCurrentValue) * 100;

        NaverNewsResponse newsData = naverNewsService.getNews(query);

        return new PortfolioSummary(stocks, totalAssetValue, totalProfit, investmentDividendYield, marketDividendYield, newsData);
    }
}
