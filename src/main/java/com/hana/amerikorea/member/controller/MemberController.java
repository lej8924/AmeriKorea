package com.hana.amerikorea.member.controller;

import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/sign-up")
//    public String memberJoin(@ModelAttribute SignUpRequest signUpRequest) {
//        memberService.insertMember(signUpRequest);
//        return "redirect:/member/sign-in"; // 회원가입 완료 후 로그인 페이지로 리다이렉트
//    }
//
//    @GetMapping("/sign-in")
//    public String showLoginForm(Model model) {
//        model.addAttribute("signUpRequest", new SignUpRequest());
//        return "page/sign-in";
//    }
    @PostMapping("/sign-up")
    public String memberJoin(@ModelAttribute SignUpRequest signUpRequest, Model model) {
        // 이메일 중복 체크
        if (memberService.isEmailDuplicate(signUpRequest.getEmail())) {
            model.addAttribute("emailError", "이미 존재하는 이메일입니다.");
            return "page/sign-up";
        }

        // 비밀번호 일치 여부 확인
        if (!signUpRequest.getPassword().equals(signUpRequest.getPasswordCheck())) {
            model.addAttribute("passwordError", "비밀번호가 일치하지 않습니다.");
            return "page/sign-up";
        }

        // 회원 가입 처리
        try {
            memberService.insertMember(signUpRequest);
        } catch (IllegalArgumentException e) {
            model.addAttribute("signupError", e.getMessage());
            return "page/sign-up";
        }

        return "redirect:/member/sign-in"; // 회원가입 완료 후 로그인 페이지로 리다이렉트
    }

    @GetMapping("/check-email")
    @ResponseBody
    public boolean checkEmail(@RequestParam("email") String email) {
        return memberService.isEmailDuplicate(email);
    }
}
