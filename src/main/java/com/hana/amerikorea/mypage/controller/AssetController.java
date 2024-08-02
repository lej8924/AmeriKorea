package com.hana.amerikorea.mypage.controller;

import com.hana.amerikorea.mypage.service.AssetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AssetController {

    @Autowired
    private AssetService assetService;

    // 자산 추가 페이지
    @RequestMapping("/asset_add")
    public ModelAndView asset_list(HttpServletRequest request) {

    }
}
