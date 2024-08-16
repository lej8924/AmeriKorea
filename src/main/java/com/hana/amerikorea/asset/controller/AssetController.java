package com.hana.amerikorea.asset.controller;

import com.hana.amerikorea.api.service.ApiService;
import com.hana.amerikorea.api.util.TokenManager;
import com.hana.amerikorea.asset.dto.AssetDTO;
import com.hana.amerikorea.asset.dto.request.AssetRequest;
import com.hana.amerikorea.asset.dto.response.AssetResponse;
import com.hana.amerikorea.asset.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private TokenManager tokenManager;

    // /api/asset 으로 자산목록 페이지 매핑
    @GetMapping()
    public String assetList(Model model) {
        List<AssetResponse> assets = assetService.getAllAssets();
        List<String> stockNames = assetService.getAllStocks();
        AssetDTO asset = new AssetDTO();

        model.addAttribute("assets", assets);
        model.addAttribute("stockNames", stockNames);
        model.addAttribute("asset", asset);

        return "asset/asset-list";
    }

    // /api/asset/add 로 postmapping
    @PostMapping("/add")
    public String addAsset(@ModelAttribute AssetRequest asset) throws ExecutionException, InterruptedException {
        assetService.saveAsset(asset);
        return "redirect:/api/asset"; // Redirect to the list of assets after saving
    }

    // /api/asset/detail 로 상세보기 페이지 매핑
    @GetMapping("/detail")
    public String detail(@RequestParam("ticker") String tickerSymbol, Model model) {
        AssetDTO assetData = assetService.getAssetById(tickerSymbol);
        model.addAttribute("assetData", assetData);

        // 차트 데이터 가져오기 (비동기 처리)
        Mono<String> chartScriptMono = assetService.getTradingViewChartScript();
        chartScriptMono.subscribe(chartScript -> {
            model.addAttribute("chartScript", chartScript);
        });


//        String apiKey = "inquire-daily-price";
//
//        Map<String, String> additionalParams = new HashMap<>();
//        additionalParams.put("fid_input_date_1", "20240805");
//        additionalParams.put("fid_input_date_2", "20240811");
//        additionalParams.put("fid_input_iscd", "005930");

        // API 호출 및 결과 처리
//        return apiService.callApi(apiKey, additionalParams)
//                .doOnNext(result -> {
//                    System.out.println("API 호출 성공: " + result);
//                    model.addAttribute("res", result);
//                })
//                .onErrorResume(error -> {
//                    System.err.println("API 호출 실패: " + error.getMessage());
//                    model.addAttribute("error", "API 호출 실패: " + error.getMessage());
//                    return Mono.empty(); // 에러 시 빈 값을 반환하거나 에러 페이지로 리다이렉트 가능
//                })
//                .thenReturn("asset/asset-detail"); // 뷰 이름 반환
//        }

        return "asset/asset-detail";
    }

    // /api/asset/detail/edit 로 상세보기에서 편집 매핑
    @PutMapping("/detail/edit")
    public String editAsset(@RequestParam("ticker") String tickerSymbol, Model model, @ModelAttribute AssetDTO pastAsset) {
        AssetDTO asset = assetService.getAssetById(tickerSymbol);
        System.out.println(asset);
        System.out.println(pastAsset);

        boolean checkChange = assetService.editAsset(asset, pastAsset);

        if (checkChange) {
            System.out.println(asset);
            System.out.println(pastAsset);
        }

        model.addAttribute("asset", asset);

        return "redirect:/api/asset/detail?ticker=" + tickerSymbol;
    }

    // 게시글 삭제
    @PostMapping("/delete")
    public String deletePost(@RequestParam("ticker") final String tickerSymbol) {
        assetService.deleteAsset(tickerSymbol);

        return "redirect:/api/asset";
    }
}
