package com.hana.amerikorea.member.controller;

import com.hana.amerikorea.member.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/member/sign-in")
    public String showLoginPage() {
        return "page/sign-in";
    }

    @GetMapping("/member/pwd-find")
    public String showPwdFindPage() {
        return "page/pwd-find"; // pwd-find 비번찾기 창을 반환
    }

    @GetMapping("/member/logout")
    public String logout() {
        return "redirect:/member/sign-in"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }
}
