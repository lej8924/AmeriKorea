package com.hana.amerikorea.member.controller;

import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/sign-up")
    public String showSignUpForm(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "page/sign-up";
    }

    @PostMapping("/sign-up")
    public String memberJoin(@ModelAttribute SignUpRequest signUpRequest) {
        memberService.insertMember(signUpRequest);
        return "redirect:/member/sign-in"; // 회원가입 완료 후 로그인 페이지로 리다이렉트
    }
//
//    @GetMapping("/sign-in")
//    public String showLoginForm(Model model) {
//        model.addAttribute("signUpRequest", new SignUpRequest());
//        return "page/sign-in";
//    }
}
