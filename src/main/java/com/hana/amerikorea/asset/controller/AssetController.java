package com.hana.amerikorea.asset.controller;

import com.hana.amerikorea.asset.dto.AssetDTO;
import com.hana.amerikorea.asset.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    // /api/asset 으로 자산목록 페이지 매핑
    @GetMapping()
    public String assetList(Model model) {
        List<AssetDTO> assets = assetService.getAllAssets();
        List<String> stockNames = assetService.getAllStocks();
        AssetDTO asset = new AssetDTO();
        model.addAttribute("assets", assets);
        model.addAttribute("stockNames", stockNames);
        model.addAttribute("asset", asset);

        return "asset/asset-list";
    }

    // /api/asset/add 로 postmapping
    @PostMapping("/add")
    public String addAsset(@ModelAttribute AssetDTO asset) {
        assetService.saveAsset(asset);
        return "redirect:/api/asset"; // Redirect to the list of assets after saving
    }

    // /api/asset/detail 로 상세보기 페이지 매핑
    @GetMapping("/detail")
    public String detail(@RequestParam("id") long assetId, Model model) {
        AssetDTO asset = assetService.getAssetById(assetId);
        model.addAttribute("asset", asset);

        // 구현할 것:
        // api를 이용해서 해당 종목에 대한 모든 정보 가져와서 model에 전달
        // stock 정보들, tradingview 차트 데이터

        return "asset/asset-detail";
    }

    // /api/asset/detail/edit 로 상세보기에서 편집 매핑
    @PutMapping("/detail/edit")
    public String editAsset(@RequestParam("id") long assetId, Model model, @ModelAttribute AssetDTO pastAsset) {
        AssetDTO asset = assetService.getAssetById(assetId);
        System.out.println(asset);
        System.out.println(pastAsset);

        boolean checkChange = assetService.editAsset(asset, pastAsset);

        if (checkChange) {
            System.out.println(asset);
            System.out.println(pastAsset);
        }

        model.addAttribute("asset", asset);

        return "redirect:/api/asset/detail?id=" + assetId;
    }
}
