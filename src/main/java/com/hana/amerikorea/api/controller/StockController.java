package com.hana.amerikorea.api.controller;

import com.hana.amerikorea.api.service.ApiCompromisedService;
import com.hana.amerikorea.asset.dto.AssetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
public class StockController {

    private final ApiCompromisedService apiCompromisedService;

    @Autowired
    public StockController(ApiCompromisedService apiCompromisedService) {
        this.apiCompromisedService= apiCompromisedService;
    }

    @GetMapping("/getAssetData")
    public AssetDTO getAssetData(
            @RequestParam long assetID,
            @RequestParam String assetName,
            @RequestParam long assetAmount,
            @RequestParam String stockName,
            @RequestParam String purchaseDate,
            @RequestParam boolean isKorean
    ) throws IOException {
        try {
            return apiCompromisedService.createAssetDTO(assetID, assetName, assetAmount, stockName, purchaseDate, isKorean);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error processing request", e);
        }
    }


}