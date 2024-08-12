package com.hana.amerikorea.global;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalController {

    @ModelAttribute
    public void addRequestURIToModel(HttpServletRequest request, Model model) {
        model.addAttribute("requestURI", request.getRequestURI());
    }
}

