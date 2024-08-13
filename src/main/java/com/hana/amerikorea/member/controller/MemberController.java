package com.hana.amerikorea.member.controller;

import com.hana.amerikorea.member.constants.SessionConstants;
import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.repository.MemberRepository;
import com.hana.amerikorea.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.System.out;


@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/sign-up")
    public String showSignUpForm(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "page/sign-up";
    }

    @PostMapping("/member/sign-up")
    public String memberJoin(@ModelAttribute @Valid SignUpRequest signUpRequest, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "page/sign-up";
        }

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
    @GetMapping("/member/profile")
    public String showMemberProfile(Model model, @AuthenticationPrincipal Member currentMember) {
        // 현재 로그인된 사용자의 ID로 최신의 회원 정보를 DB에서 가져옴
        Member member = memberService.findMemberById(currentMember.getId());

        // 최신의 회원 정보를 모델에 추가
        model.addAttribute("member", member);
        return "page/profile";
    }


    @PostMapping("/member/update")
    public String updateMemberProfile(
            Member updatedMember,
            @AuthenticationPrincipal Member currentMember,
            Model model) {

        updatedMember.setId(currentMember.getId());

        boolean updateSuccessful = memberService.updateMember(updatedMember);

        if (!updateSuccessful) {
            model.addAttribute("updateError", "회원 정보 업데이트에 실패했습니다.");
            return "redirect:/member/profile";
        }


        return "redirect:/api/portfolio";
    }


    @GetMapping("/member/check-email")
    @ResponseBody
    public boolean checkEmail(@RequestParam("email") String email) {
        return memberService.isEmailDuplicate(email);
    }
}

