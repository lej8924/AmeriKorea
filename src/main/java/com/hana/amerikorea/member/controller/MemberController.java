package com.hana.amerikorea.member.controller;

import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    private MemberService memberService;

//    @GetMapping("/signup")
//    public String showSignUpForm(Model model) {
//        model.addAttribute("signUpRequest", new SignUpRequest());
//        return "member/sign-up";
//    }

    @PostMapping("/signup")
    public String memberJoin(@ModelAttribute SignUpRequest signUpRequest) {
                memberService.insertMember(signUpRequest);
        return "redirect:/signin"; // 회원가입 완료 후 로그인 페이지로 리다이렉트
    }

}
