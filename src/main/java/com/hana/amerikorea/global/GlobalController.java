package com.hana.amerikorea.global;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalController {

    @ModelAttribute
    public void addRequestURIToModel(HttpServletRequest request, Model model) {
        String url = request.getRequestURI();
        String lastSegment = url.substring(url.lastIndexOf('/') + 1);
        String capitalizedSegment = lastSegment.substring(0, 1).toUpperCase() + lastSegment.substring(1);

        model.addAttribute("pageTitle", capitalizedSegment);
        model.addAttribute("requestURI", url);
    }
}

