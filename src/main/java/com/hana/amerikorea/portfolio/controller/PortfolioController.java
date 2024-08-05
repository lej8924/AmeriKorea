package com.hana.amerikorea.portfolio.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PortfolioController {

    @GetMapping("/portfolio")
    public String getStocks(Model model){

        model.addAttribute("data", "hello!!");
        return "page/dashboard";
    }

}
