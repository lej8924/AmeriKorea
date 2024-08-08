package com.hana.amerikorea.portfolio.controller;

import com.hana.amerikorea.portfolio.dto.request.NaverNewsRequest;
import com.hana.amerikorea.portfolio.dto.response.PortfolioSummary;
import com.hana.amerikorea.portfolio.dto.response.StockResponse;
import com.hana.amerikorea.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    PortfolioService portfolioService;

    @GetMapping
    public ModelAndView getStocks(ModelAndView mav,@RequestBody NaverNewsRequest naverNewsRequest){
        PortfolioSummary summary = portfolioService.getPortfolioSummary(naverNewsRequest);
        mav.addObject("summary", summary);
        mav.addObject("stocks", summary.stocks());
        mav.addObject("newsData", summary.naverNewsResponse().getItems());
        mav.setViewName("page/dashboard");
        return mav;
    }
    @GetMapping("/test")
    public ModelAndView getMine(ModelAndView mav){
        PortfolioSummary summary = portfolioService.getPortfolioSummary(naverNewsRequest);
        mav.setViewName("page/mypage");
        return mav;
    }

    @GetMapping("/monthly-dividends")
    public Map<String, Double> getMonthlyDividends() {
        return portfolioService.calculateMonthlyDividends();
    }


}
