package com.hana.amerikorea.portfolio.service;

import com.hana.amerikorea.portfolio.dto.response.NaverNewsResponse;
import com.hana.amerikorea.portfolio.dto.response.PortfolioSummary;
import com.hana.amerikorea.asset.dto.response.AssetResponse;
import com.hana.amerikorea.portfolio.domain.Asset;
import com.hana.amerikorea.portfolio.repository.AssetRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PortfolioService {

    private AssetRepository assetRepository;
    private final NaverNewsService naverNewsService;

    public PortfolioSummary getPortfolioSummary() {
        List<AssetResponse> stocks = assetRepository.findAllSorted(Sort.by(Sort.Direction.DESC, "quantity"))
                .stream()
                .map(Asset::toDto)
                .collect(Collectors.toList());

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
        double totalMonthlyDividends = 0;

        NaverNewsResponse newsData = naverNewsService.getNews(query);

        return new PortfolioSummary(stocks, totalAssetValue, totalProfit, investmentDividendYield, marketDividendYield, newsData);
    }

//    public Map<String, Double> calculateMonthlyDividends() {
//        List<Asset> stocks =  assetRepository.findAll();
//        Map<String, Double> monthlyDividends = new HashMap<>();
//        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
//
//        for (String month : months) {
//            monthlyDividends.put(month, 0.0);
//        }
//
//        for (Asset stock : stocks) {
//            double monthlyDividend = stock.getMonthlyDividend();
//            switch (stock.getDividendFrequency()) {
//                case MONTHLY:
//                    for (String month : months) {
//                        monthlyDividends.put(month, monthlyDividends.get(month) + monthlyDividend);
//                    }
//                    break;
//                case QUARTERLY:
//                    for (int i = 0; i < 12; i += 3) {
//                        monthlyDividends.put(months[i], monthlyDividends.get(months[i]) + monthlyDividend);
//                    }
//                    break;
//                case SEMIANNUALLY:
//                    for (int i = 0; i < 12; i += 6) {
//                        monthlyDividends.put(months[i], monthlyDividends.get(months[i]) + monthlyDividend);
//                    }
//                    break;
//                case ANNUALLY:
//                    monthlyDividends.put(stock.getDividendMonth(), monthlyDividends.get(stock.getDividendMonth()) + monthlyDividend);
//                    break;
//            }
//        }
//
//        return monthlyDividends;
//    }
}
