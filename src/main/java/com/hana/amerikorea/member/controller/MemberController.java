package com.hana.amerikorea.member.controller;

import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/signup")
    public String memberJoin(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "member/sign-up";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute SignUpRequest signUpRequest) {
        if (!signUpRequest.getPassword().equals(signUpRequest.getPasswordCheck())) {
            return "redirect:/signup?error=passwordMismatch";
        }
        memberService.saveMember(signUpRequest);
        return "redirect:/sign-in";
    }
}
