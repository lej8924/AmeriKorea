package com.hana.amerikorea.portfolio.controller;

import com.hana.amerikorea.portfolio.dto.response.DividendInfo;
import com.hana.amerikorea.portfolio.dto.response.PortfolioSummary;
import com.hana.amerikorea.asset.dto.response.AssetResponse;
import com.hana.amerikorea.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    PortfolioService portfolioService;

    @GetMapping
    public ModelAndView getStocks(ModelAndView mav, @AuthenticationPrincipal UserDetails userDetails){

        String email = userDetails.getUsername();
        System.out.println("user email 출력:" + email);

        PortfolioSummary summary = portfolioService.getPortfolioSummary(email);

        // 총합 계산
        int totalQuantity = summary.stocks().stream()
                .mapToInt(AssetResponse::getQuantity)
                .sum();

        mav.addObject("summary", summary);
        mav.addObject("stocks", summary.stocks());
        mav.addObject("totalQuantity", totalQuantity); // 총합을 ModelAndView에 추가
        mav.addObject("dividends",summary.dividendInfos());

        // 뉴스 데이터가 null일 경우 기본 값을 설정
        if (summary.naverNewsResponse() != null && summary.naverNewsResponse().getItems() != null) {
            mav.addObject("newsData", summary.naverNewsResponse().getItems());
        } else {
            mav.addObject("newsData", Collections.emptyList());  // 기본 값으로 빈 리스트 추가
        }
        mav.setViewName("page/dashboard");
        return mav;
    }


}
