package com.hana.amerikorea.asset.service;

import com.hana.amerikorea.asset.dto.AssetDTO;
import com.hana.amerikorea.asset.dto.request.AssetRequest;
import com.hana.amerikorea.asset.dto.response.AssetResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AssetService {

    List<AssetResponse> getAllAssets();

    void saveAsset(AssetRequest asset) throws ExecutionException, InterruptedException;

    List<String> getAllStocks();

    AssetDTO getAssetById(String tickerSymbol);

    boolean editAsset(AssetDTO asset, AssetDTO pastAsset);

    Mono<String> getTradingViewChartScript();

    void deleteAsset(String tickerSymbol);
}
