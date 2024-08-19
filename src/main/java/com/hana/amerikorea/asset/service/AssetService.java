package com.hana.amerikorea.asset.service;

import com.hana.amerikorea.asset.dto.request.AssetRequest;
import com.hana.amerikorea.asset.dto.response.AssetResponse;
import com.hana.amerikorea.chart.dto.response.ChartResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface AssetService {

    List<AssetResponse> getAllAssets(String email);

    void saveAsset(AssetRequest asset,String email) throws ExecutionException, InterruptedException;

    List<String> getAllStocks();

    AssetResponse getAssetById(String tickerSymbol);

    boolean editAsset(AssetResponse asset, AssetResponse pastAsset);

    Mono<String> getTradingViewChartScript();

    void deleteAsset(String tickerSymbol);

    List<Map<String, Object>> getChartDataList(String stockName, boolean country) throws ExecutionException, InterruptedException;
}
