package com.hana.amerikorea.login.controller;

import com.hana.amerikorea.login.dto.LoginRequest;
import com.hana.amerikorea.login.domain.Login;
import com.hana.amerikorea.login.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/sign-in")
    public String showLoginPage() {
        return "page/sign-in";
    } //로그인 페이지를 반환

    @GetMapping("/sign-up")
    public String showSignUpPage() {
        return "page/sign-up";
    } // 회원가입 페이지를 반환

    @PostMapping("/sign-in")
    public String login(LoginRequest loginRequest, HttpServletResponse response, HttpSession session,Model model) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        Login login=loginService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        if(login==null) {
            out.println("<script>");
            out.println("alert('가입되지 않은 회원이거나 비밀번호가 틀렸습니다!');");
            out.println("history.go(-1);");
            out.println("</script>");
            out.flush();
        }else {
            session.setAttribute("login", login);
            return "redirect:/profile";
        }


        return null;
    }

    @GetMapping("/profile")
    public String showprofilePage() {
        return "page/profile";
    } // 포폴 페이지를 반환
}
