package com.hana.amerikorea.asset.service;

import com.hana.amerikorea.asset.dto.AssetDTO;
import com.hana.amerikorea.portfolio.domain.Asset;
import com.hana.amerikorea.portfolio.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepo;

    @Autowired
    private TradingViewService tradingViewService;


    @Override
    public List<AssetDTO> getAllAssets() {

        List<AssetDTO> assetDTOList = new ArrayList<>();
        assetRepo.findAll().forEach(asset -> {
            AssetDTO assetDTO = AssetDTO.builder()
                    .tickerSymbol(asset.getTickerSymbol())
                    .stockName(asset.getStockName())
                    .sector(asset.getSector())
                    .industry(asset.getIndustry())
                    .exchange(asset.getExchange())
                    .country(asset.getCountry())
                    .quantity(asset.getQuantity())
                    .assetValue(asset.getAssetValue())
                    .purchasePrice(asset.getPurchasePrice())
                    .profit(asset.getProfit())
                    .currentPrice(asset.getCurrentPrice())
                    .dividendMonth(asset.getDividendMonth())
                    .investmentDividendYield(asset.getInvestmentDividendYield())
                    .dividendPerShare(asset.getDividendPerShare())
                    .dividendFrequency(asset.getDividendFrequency())
                    .build();

            assetDTOList.add(assetDTO);
        });

        return assetDTOList;
    }

    @Override
    public void saveAsset(AssetDTO asset) {
        ////////////////////////////// ticker를 이용해 api로 데이터 가져와서 저장하기 /////////////////////////////////////////////////
        Asset tempAsset = new Asset(asset.getStockName(), asset.getQuantity(), asset.getPurchasePrice());
        assetRepo.save(tempAsset);
    }

    @Override
    public List<String> getAllStocks() {
        List<String> stocksNames = new ArrayList<>();
        assetRepo.findAll().forEach(stock -> stocksNames.add(stock.getStockName()));
        return stocksNames;
    }

    @Override
    public AssetDTO getAssetById(String tickerSymbol) {
        Optional<Asset> optionalAsset = assetRepo.findById(tickerSymbol);

        // 존재하지 않으면 예외처리
        if (optionalAsset.isEmpty()) {
            System.out.println("Asset not found");
            return null; // 예시로 null 반환
        }

        Asset asset = optionalAsset.get();

        AssetDTO assetDTO = AssetDTO.builder()
                .tickerSymbol(asset.getTickerSymbol())
                .stockName(asset.getStockName())
                .sector(asset.getSector())
                .industry(asset.getIndustry())
                .exchange(asset.getExchange())
                .country(asset.getCountry())
                .quantity(asset.getQuantity())
                .assetValue(asset.getAssetValue())
                .purchasePrice(asset.getPurchasePrice())
                .profit(asset.getProfit())
                .currentPrice(asset.getCurrentPrice())
                .dividendMonth(asset.getDividendMonth())
                .investmentDividendYield(asset.getInvestmentDividendYield())
                .dividendPerShare(asset.getDividendPerShare())
                .dividendFrequency(asset.getDividendFrequency())
                .build();

        return assetDTO;
    }

    @Override
    public boolean editAsset(AssetDTO assetDTO, AssetDTO pastAssetDTO) {

        boolean checkChange = false;

        // 주식 수량과 평균 단가 중 하나라도 바뀐 경우
        if (assetDTO.getQuantity() != pastAssetDTO.getQuantity() || assetDTO.getPurchasePrice() != pastAssetDTO.getPurchasePrice()) {
            assetDTO.setQuantity(pastAssetDTO.getQuantity());
            assetDTO.setPurchasePrice(pastAssetDTO.getPurchasePrice());

            double quantity = assetDTO.getQuantity();
            double purchasePrice = assetDTO.getPurchasePrice();

            assetDTO.setAssetValue(quantity * assetDTO.getCurrentPrice());
            assetDTO.setProfit((assetDTO.getCurrentPrice() - purchasePrice) * quantity);

            checkChange = true;
        }

        // 값을 변경
        if (checkChange) {
            // 기존의 asset 객체를 가져와 수정한 후 저장
            Optional<Asset> existingAsset = assetRepo.findById(assetDTO.getTickerSymbol());

            Asset asset = existingAsset.get();

            asset.setQuantity(assetDTO.getQuantity());
            asset.setPurchasePrice(assetDTO.getPurchasePrice());
            assetRepo.save(asset);
        }

        return checkChange;
    }

/////////////////////////////////// api 호출로 수정/////////////////////////////////////////////////////////////
    private double getCurrentPrice(String assetName) {
        return 10000;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Mono<String> getTradingViewChartScript() {
        return tradingViewService.getTradingViewChartScript();
    }

    @Override
    public void deleteAsset(String tickerSymbol) {
        if (assetRepo.existsById(tickerSymbol)) {
            assetRepo.deleteById(tickerSymbol);
        } else {
            System.out.println("Asset with ID " + tickerSymbol + " does not exist.");
        }
    }


}
