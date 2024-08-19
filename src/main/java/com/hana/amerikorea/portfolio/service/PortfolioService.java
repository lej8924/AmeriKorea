package com.hana.amerikorea.portfolio.service;

import com.hana.amerikorea.api.service.ApiCompromisedService;
import com.hana.amerikorea.asset.repository.DividendRepository;
import com.hana.amerikorea.portfolio.dto.response.DividendInfo;
import com.hana.amerikorea.portfolio.dto.response.NaverNewsResponse;
import com.hana.amerikorea.portfolio.dto.response.PortfolioSummary;
import com.hana.amerikorea.asset.dto.response.AssetResponse;
import com.hana.amerikorea.asset.domain.Dividend;
import com.hana.amerikorea.asset.repository.AssetRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PortfolioService {

    private AssetRepository assetRepository;
    private final NaverNewsService naverNewsService;
    private DividendRepository dividendRepository;
    private ApiCompromisedService apiCompromisedService;

    public PortfolioSummary getPortfolioSummary(String email) {

        List<AssetResponse> assetResponseList = new ArrayList<>();
        List<DividendInfo> dividendInfos = new ArrayList<>();

        assetRepository.findByMemberEmail(email, Sort.by(Sort.Direction.DESC, "quantity")).forEach(asset -> {
            try {
                System.out.println("쿼리 파라미터 : " + asset.getStockInfo().getStockName() + asset.getQuantity() + asset.getPurchasePrice());

                // API를 통해 현재 주가 등의 정보 획득
                AssetResponse apiResponse = apiCompromisedService.createAssetDTO(
                        asset.getStockInfo().getStockName(),
                        asset.getQuantity(),
                        asset.getPurchasePrice(),
                        true
                );

                // 배당 정보 추가
                Map<LocalDate, Double> dividends = dividendRepository.findByStockInfoTickerSymbol(asset.getStockInfo().getTickerSymbol()).stream()
                        .collect(Collectors.toMap(
                                Dividend::getDividendDate,
                                Dividend::getDividendAmount
                        ));

                dividends.forEach((date, price) -> {
                    DividendInfo info = new DividendInfo(asset.getStockInfo().getStockName(), date.toString(), price.toString());
                    dividendInfos.add(info);
                });

                apiResponse.setDividends(dividends);

                assetResponseList.add(apiResponse);

            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException("Error fetching asset data", e);
            }
        });

        if (assetResponseList.isEmpty()) {
            // 포트폴리오가 비어 있을 때 처리
            return new PortfolioSummary(assetResponseList, 0.0, 0.0, 0.0, 0.0, null, Arrays.asList());
        }

        // 가장 첫 번째 주식 정보를 기준으로 네이버 뉴스 검색
        String query = assetResponseList.get(0).getStockName();

        // 총 자산 가치 계산
        Double totalAssetValue = assetResponseList.stream()
                .mapToDouble(AssetResponse::getAssetValue)
                .sum();

        // 총 수익 계산
        Double totalProfit = assetResponseList.stream()
                .mapToDouble(AssetResponse::getProfit)
                .sum();

        // 총 투자 금액 계산
        double totalInvestment = assetResponseList.stream()
                .mapToDouble(stock -> stock.getPurchasePrice() * stock.getQuantity())
                .sum();

        // 총 배당금 계산
        double totalDividend = assetResponseList.stream()
                .mapToDouble(stock -> stock.getInvestmentDividendYield() * stock.getQuantity())
                .sum();

        // 투자 배당 수익률 계산
        double investmentDividendYield = (totalDividend / totalInvestment) * 100;

        // 현재 총 가치 계산
        double totalCurrentValue = assetResponseList.stream()
                .mapToDouble(stock -> stock.getCurrentPrice() * stock.getQuantity())
                .sum();

        // 시가 배당 수익률 계산
        double marketDividendYield = (totalDividend / totalCurrentValue) * 100;

        // 네이버 뉴스 데이터 가져오기
        NaverNewsResponse newsData = naverNewsService.getNews(query);

        // PortfolioSummary 생성 및 반환
        return new PortfolioSummary(assetResponseList, totalAssetValue, totalProfit, investmentDividendYield, marketDividendYield, newsData,dividendInfos);
    }

}
