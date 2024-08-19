package com.hana.amerikorea.asset.controller;

import com.hana.amerikorea.api.service.ApiCompromisedService;
import com.hana.amerikorea.api.service.ApiService;
import com.hana.amerikorea.api.util.TokenManager;
import com.hana.amerikorea.asset.dto.request.AssetRequest;
import com.hana.amerikorea.asset.dto.response.AssetResponse;
import com.hana.amerikorea.asset.service.AssetService;
import com.hana.amerikorea.chart.dto.response.ChartResponse;
import com.hana.amerikorea.portfolio.dto.response.NaverNewsResponse;
import com.hana.amerikorea.portfolio.service.NaverNewsService;
import groovy.transform.AutoExternalize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
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
    private NaverNewsService naverNewsService;

    @Autowired
    private ApiCompromisedService apiCompromisedService;


    // /api/asset 으로 자산목록 페이지 매핑
    @GetMapping()
    public String assetList(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();

        List<AssetResponse> assets = assetService.getAllAssets(email);
        List<String> stockNames = assetService.getAllStocks();
        AssetRequest asset = new AssetRequest();

        // stockOptions를 assetList 함수 내부에 정의
        List<String[]> stockOptions = new ArrayList<>();
        stockOptions.add(new String[]{"true", "한국 주식"});
        stockOptions.add(new String[]{"false", "미국 주식"});

        model.addAttribute("assets", assets);
        model.addAttribute("stockNames", stockNames);
        model.addAttribute("asset", asset);
        model.addAttribute("stockOptions", stockOptions);

        return "asset/asset-list";
    }

    // /api/asset/add 로 postmapping
    @PostMapping("/add")
    public String addAsset(@ModelAttribute AssetRequest asset,@AuthenticationPrincipal UserDetails userDetails) throws ExecutionException, InterruptedException {
        assetService.saveAsset(asset,userDetails.getUsername());
        return "redirect:/api/asset"; // Redirect to the list of assets after saving
    }

    // /api/asset/detail 로 상세보기 페이지 매핑
    @GetMapping("/detail")

    public String detail(@RequestParam("ticker") String tickerSymbol, Model model, @AuthenticationPrincipal UserDetails userDetails) throws ExecutionException, InterruptedException {
        AssetResponse assetData = assetService.getAssetById(tickerSymbol, userDetails.getUsername());
        model.addAttribute("assetData", assetData);

        NaverNewsResponse newsData = naverNewsService.getNews(assetData.getStockName());
        model.addAttribute("newsData",newsData.getItems());

        // 차트 데이터 가져오기 (비동기 처리)
        Mono<String> chartScriptMono = assetService.getTradingViewChartScript();
        chartScriptMono.subscribe(chartScript -> {
            model.addAttribute("chartScript", chartScript);
        });


        List<Map<String, Object>> chartDataList = assetService.getChartDataList(assetData.getStockName(), assetData.isCountry());

        model.addAttribute("data", chartDataList);



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
    public String editAsset(@RequestParam("ticker") String tickerSymbol, Model model, @ModelAttribute AssetResponse pastAsset,@AuthenticationPrincipal UserDetails userDetails) {
        AssetResponse asset = assetService.getAssetById(tickerSymbol, userDetails.getUsername());
        System.out.println(asset);
        System.out.println(pastAsset);

        boolean checkChange = assetService.editAsset(asset, pastAsset,userDetails.getUsername());

        if (checkChange) {
            System.out.println(asset);
            System.out.println(pastAsset);
        }

        model.addAttribute("asset", asset);

        return "redirect:/api/asset/detail?ticker=" + tickerSymbol;
    }

    // 게시글 삭제
    @PostMapping("/delete")
    public String deletePost(@RequestParam("ticker") final String tickerSymbol,@AuthenticationPrincipal UserDetails userDetails) {
        assetService.deleteAsset(tickerSymbol,userDetails.getUsername());

        return "redirect:/api/asset";
    }
}
