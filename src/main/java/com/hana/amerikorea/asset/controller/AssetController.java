package com.hana.amerikorea.asset.controller;

import com.hana.amerikorea.asset.domain.AssetDomain;
import com.hana.amerikorea.asset.dto.AssetDTO;
import com.hana.amerikorea.asset.service.AssetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AssetController {

    @Autowired
    private AssetService assetService;

    //
    @GetMapping("/asset")
    public String assetList(Model model) {
        List<AssetDTO> assets = assetService.getAllAssets();
        model.addAttribute("assets", assets);
        return "asset/asset_list";
    }

    // Display the asset addition form (modal content)
    @GetMapping("/asset_add")
    public String addAssetForm(Model model) {
        model.addAttribute("asset", new AssetDTO());
        return "asset/asset_add"; // This returns the fragment for the modal content
    }

    // Handle form submission
    @PostMapping("/asset_add")
    public String addAsset(AssetDTO asset, Model model) {
        assetService.saveAsset(asset);
        return "redirect:/asset"; // Redirect to the list of assets after saving
    }
}
