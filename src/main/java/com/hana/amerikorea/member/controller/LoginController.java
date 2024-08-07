package com.hana.amerikorea.member.controller;

import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.PrintWriter;
import java.io.IOException;
@RequestMapping("/member")
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

//    @GetMapping("/sign-up")
//    public String showSignUpPage() {
//        return "page/sign-up";
//    } // 회원가입 페이지를 반환
//


    @PostMapping("/sign-in")
    public String login(SignUpRequest signUpRequest, HttpServletResponse response, HttpSession session, Model model) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        Member login=loginService.authenticate(signUpRequest.getEmail(), signUpRequest.getPassword());

        if(login==null) {
            out.println("<script>");
            out.println("alert('가입되지 않은 회원이거나 비밀번호가 틀렸습니다!');");
            out.println("history.go(-1);");
            out.println("</script>");
            out.flush();
        }else {
            session.setAttribute("login", login);
            return "redirect:/member/profile";
        }


        return null;
    }

    @GetMapping("/profile")
    public String showprofilePage() {
        return "page/profile";
    } // 포폴 페이지를 반환
}