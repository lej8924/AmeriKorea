package com.hana.amerikorea.member.controller;

import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/memberjoin")
    public ResponseEntity<String> memberJoin( @RequestBody SignUpRequest signUpRequest) {
        memberService.insertMember(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Member created successfully");
    }


    @PostMapping("/emailcheck")
    public boolean emailCheck(@RequestBody SignUpRequest signUpRequest) {
        return memberService.emailCheck(signUpRequest);
    }





}
