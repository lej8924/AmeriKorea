package com.hana.amerikorea.asset.controller;

import com.hana.amerikorea.asset.dto.AssetDTO;
import com.hana.amerikorea.asset.service.AssetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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

    // 자산 추가 페이지
    @GetMapping("/asset_add")
    public String asset_add(HttpServletRequest request) {

        return "asset/asset_add";
    }
}
