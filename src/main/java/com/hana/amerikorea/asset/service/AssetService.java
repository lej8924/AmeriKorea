package com.hana.amerikorea.asset.service;

import com.hana.amerikorea.asset.dto.AssetDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AssetService {

    List<AssetDTO> getAllAssets();

    void saveAsset(AssetDTO asset);

    List<String> getAllStocks();

    AssetDTO getAssetById(String tickerSymbol);

    boolean editAsset(AssetDTO asset, AssetDTO pastAsset);

    Mono<String> getTradingViewChartScript();

    void deleteAsset(String tickerSymbol);
}
