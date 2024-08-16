//package com.hana.amerikorea.portfolio.domain.init;
//
//import com.hana.amerikorea.portfolio.domain.Asset;
//import com.hana.amerikorea.portfolio.domain.Dividend;
//import com.hana.amerikorea.portfolio.repository.AssetRepository;
//import com.hana.amerikorea.portfolio.repository.DividendRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import jakarta.annotation.PostConstruct;
//import java.time.LocalDate;
//import java.util.List;
//
//@Component
//public class DividendInitializer {
//
//    private final AssetRepository assetRepository;
//    private final DividendRepository dividendRepository;
//
//    @Autowired
//    public DividendInitializer(AssetRepository assetRepository, DividendRepository dividendRepository) {
//        this.assetRepository = assetRepository;
//        this.dividendRepository = dividendRepository;
//    }
//
//    @PostConstruct
//    public void init() {
//        // 이미 저장된 Asset 객체들을 가져옴
//        List<Asset> assets = assetRepository.findAll();
//
//        // 각 Asset에 대한 Dividend를 초기화
//        for (Asset asset : assets) {
//            initializeDividends(asset);
//        }
//    }
//
//    private void initializeDividends(Asset asset) {
//        // 배당금이 있는 경우에만 초기화
//        if (asset.getAnnualDividend() > 0) {
//            // 가정: 연간 배당금을 4개의 분기로 나누어 분기마다 지급한다고 가정
//            double quarterlyDividend = asset.getAnnualDividend() / 4;
//
//            LocalDate[] dividendDates = {
//                    LocalDate.of(2023, 3, 1),
//                    LocalDate.of(2023, 6, 1),
//                    LocalDate.of(2023, 9, 1),
//                    LocalDate.of(2023, 12, 1)
//            };
//
//            for (LocalDate date : dividendDates) {
//                Dividend dividend = new Dividend(date, quarterlyDividend * asset.getQuantity(), asset);
//                dividendRepository.save(dividend);
//            }
//        }
//    }
//}
