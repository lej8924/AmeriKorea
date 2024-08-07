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
        List<String> stockNames = assetService.getAllStocks();
        model.addAttribute("assets", assets);
        model.addAttribute("stockNames", stockNames);

        return "asset/asset-list";
    }


    // /api/asset/add 로 postmapping
    @PostMapping("/add")
    public String addAsset(AssetDTO asset) {
        assetService.saveAsset(asset);
        return "redirect:../asset"; // Redirect to the list of assets after saving
    }
}
