package com.hana.amerikorea.portfolio.controller;

import com.hana.amerikorea.portfolio.dto.response.DividendInfo;
import com.hana.amerikorea.portfolio.dto.response.PortfolioSummary;
import com.hana.amerikorea.asset.dto.response.AssetResponse;
import com.hana.amerikorea.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    PortfolioService portfolioService;

    @GetMapping
    public ModelAndView getStocks(ModelAndView mav){
        PortfolioSummary summary = portfolioService.getPortfolioSummary();

        // 총합 계산
        int totalQuantity = summary.stocks().stream()
                .mapToInt(AssetResponse::getQuantity)
                .sum();

        mav.addObject("summary", summary);
        mav.addObject("stocks", summary.stocks());
        mav.addObject("totalQuantity", totalQuantity); // 총합을 ModelAndView에 추가
        mav.addObject("newsData", summary.naverNewsResponse().getItems());

        mav.setViewName("page/dashboard");
        return mav;
    }
    @GetMapping("/test")
    public ModelAndView getMine(ModelAndView mav){
        PortfolioSummary summary = portfolioService.getPortfolioSummary();
        mav.setViewName("page/mypage");
        return mav;
    }

//    @GetMapping("/monthly-dividends")
//    @ResponseBody
//    public Map<String, Double> getMonthlyDividends() {
//        return portfolioService.calculateMonthlyDividends();
//    }

    @GetMapping("/monthly-dividends")
    @ResponseBody
    public List<DividendInfo> getMonthlyDividends() {
        // 데이터 준비
        DividendInfo samsung = new DividendInfo("삼성전자", "2024-08-12", "1000원");
        DividendInfo lg = new DividendInfo("LG전자", "2024-09-15", "1500원");
        DividendInfo skHynix = new DividendInfo("SK하이닉스", "2024-10-10", "2000원");

        return Arrays.asList(samsung, lg, skHynix);
    }

}
