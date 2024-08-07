package com.hana.amerikorea.asset.controller;

import com.hana.amerikorea.asset.dto.AssetDTO;
import com.hana.amerikorea.asset.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    //
    @GetMapping()
    public String assetList(Model model) {
        List<AssetDTO> assets = assetService.getAllAssets();
        model.addAttribute("assets", assets);
        return "asset/asset-list";
    }

    // /api/asset/add 로 getmapping
    @GetMapping("/add")
    public String addAssetForm(Model model) {
        model.addAttribute("asset", new AssetDTO());
        return "asset/asset-add"; // asset/asset-add.html 열기
    }

    // /api/asset/add 로 postmapping
    @PostMapping("/add")
    public String addAsset(AssetDTO asset, Model model) {
        assetService.saveAsset(asset);
        return "redirect:/asset/asset-list"; // Redirect to the list of assets after saving
    }
}
