package com.hana.amerikorea.portfolio.service;

import com.hana.amerikorea.portfolio.dto.response.PortfolioSummary;
import com.hana.amerikorea.portfolio.dto.response.StockResponse;
import com.hana.amerikorea.portfolio.domain.Stock;
import com.hana.amerikorea.portfolio.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    @Autowired
    private StockRepository stockRepository;

    public PortfolioSummary getPortfolioSummary() {
        List<StockResponse> stocks = stockRepository.findAll().stream()
                .map(Stock::toDto)
                .collect(Collectors.toList());

        double totalAssetValue = stocks.stream()
                .mapToDouble(StockResponse::assetValue)
                .sum();

        double totalProfit = stocks.stream()
                .mapToDouble(StockResponse::profit)
                .sum();

        double totalInvestment = stocks.stream()
                .mapToDouble(stock -> stock.purchasePrice() * stock.quantity())
                .sum();

        double totalDividend = stocks.stream()
                .mapToDouble(stock -> stock.investmentDividendYield() * stock.quantity())
                .sum();

        double investmentDividendYield = (totalDividend / totalInvestment) * 100;

        double totalCurrentValue = stocks.stream()
                .mapToDouble(stock -> stock.currentPrice() * stock.quantity())
                .sum();

        double marketDividendYield = (totalDividend / totalCurrentValue) * 100;

        return new PortfolioSummary(stocks, totalAssetValue, totalProfit, investmentDividendYield, marketDividendYield);
    }
}
