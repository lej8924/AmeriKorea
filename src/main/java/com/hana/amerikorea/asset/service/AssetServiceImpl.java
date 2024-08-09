package com.hana.amerikorea.asset.service;

import com.hana.amerikorea.asset.domain.AssetDomain;
import com.hana.amerikorea.asset.dto.AssetDTO;
import com.hana.amerikorea.asset.dto.AssetRepository;
import com.hana.amerikorea.portfolio.domain.Stock;
import com.hana.amerikorea.portfolio.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepo;

    @Autowired
    private StockRepository portfolioRepo;

    @Override
    public List<AssetDTO> getAllAssets() {

        List<AssetDTO> assetDTOList = new ArrayList<>();
        assetRepo.findAll().forEach(asset -> {
            int currentPrice = getCurrentPrice(asset.getAssetName()); // 메서드로 현재가 가져오기

            AssetDTO assetDTO = AssetDTO.builder()
                    .assetID(asset.getAssetID())
                    .assetName(asset.getAssetName())
                    .assetAmount(asset.getAssetAmount())
                    .assetBuy(asset.getAssetBuy())
                    .currentPrice(currentPrice)
                    .build();
            assetDTOList.add(assetDTO);
        });

        return assetDTOList;
    }

    @Override
    public void saveAsset(AssetDTO asset) {
        AssetDomain tempAsset = new AssetDomain(asset.getAssetName(), asset.getAssetAmount(), asset.getAssetBuy());
        assetRepo.save(tempAsset);
    }

    @Override
    public List<String> getAllStocks() {
        List<String> stocksNames = new ArrayList<>();
        portfolioRepo.findAll().forEach(portfolio -> stocksNames.add(portfolio.getStockName()));
        return stocksNames;
    }

    @Override
    public AssetDTO getAssetById(long assetId) {
        Optional<AssetDomain> optionalAsset = assetRepo.findById(assetId);

        // 존재하지 않으면 예외처리
        if (!optionalAsset.isPresent()) {
            System.out.println("Asset not found");
            return null; // 예시로 null 반환
        }

        AssetDomain asset = optionalAsset.get();

        AssetDTO assetDTO = new AssetDTO(assetId, asset.getAssetName(), asset.getAssetAmount(), asset.getAssetBuy(), getCurrentPrice(asset.getAssetName()));

        return assetDTO;
    }

    // api를 사용해서 현재가 가져오기
    // 나중에 ticker로 바꾸기
    private int getCurrentPrice(String assetName) {
        return 10000;
    }
}
