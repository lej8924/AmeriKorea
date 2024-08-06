package com.hana.amerikorea.portfolio.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @GetMapping
    public ModelAndView getStocks(ModelAndView mav){
        mav.setViewName("page/dashboard");
        return mav;
    }



}
