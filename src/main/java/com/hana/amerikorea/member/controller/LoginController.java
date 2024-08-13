package com.hana.amerikorea.member.controller;

import com.hana.amerikorea.member.constants.SessionConstants;
import com.hana.amerikorea.member.domain.Member;

import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.Random;


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

/*    @PostMapping("/member/temporary_pwd")
    public ModelAndView pwd_find_ok(SignUpRequest forgotPasswordRequest, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        Member member = loginService.findMemberByEmailAndName(forgotPasswordRequest.getEmail(), forgotPasswordRequest.getName());

        if (member == null) {
            out.println("<script>");
            out.println("alert('회원으로 검색되지 않습니다!\\n 올바른 회원정보를 입력하세요!');");
            out.println("history.back();");
            out.println("</script>");
            out.flush();
        } else {
            Random random = new Random();
            int tempPassword = random.nextInt(100000); // 0 이상 100000 미만의 정수 숫자 난수 발생
            String tempPasswordStr = Integer.toString(tempPassword); // 정수 숫자 비번을 문자열로 변경

            member.setPassword(tempPasswordStr); // 임시 비번 설정
            loginService.updatePassword(member); // 임시 비번으로 DB 수정

            ModelAndView modelAndView = new ModelAndView("page/pwd-find-ok");
            modelAndView.addObject("tempPassword", tempPasswordStr);
            return modelAndView;
        }

        return null;
    }
*/
    @GetMapping("/member/logout")
    public String logout() {
        return "redirect:/member/sign-in"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }
}
