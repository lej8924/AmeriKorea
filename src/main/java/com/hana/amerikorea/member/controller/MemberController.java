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
    public String memberJoin(@ModelAttribute  @Valid SignUpRequest signUpRequest, Model model, BindingResult bindingResult) {
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
    }// 회원가입 완료 후 로그인 페이지로 리다이렉트




    @GetMapping("/member/profile")
    public String showMemberProfile(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConstants.LOGIN_MEMBER) == null) {
            response.sendRedirect("/member/sign-in");
            return null;
        }

        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);
        Member member = memberService.findMemberById(loginMember.getId());

        if (member == null) {
            response.sendRedirect("/member/sign-in");
            return null;
        }

        model.addAttribute("member", member);
        return "page/profile";
    }

    @PostMapping("/member/update")
    public String updateMemberProfile(Member updatedMember, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConstants.LOGIN_MEMBER) == null) {
            response.sendRedirect("/member/sign-in");
            return null;
        }

        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);

        // Ensure the correct member is being updated
        updatedMember.setId(loginMember.getId());

        // Update the member information, excluding email
        memberService.updateMember(updatedMember);

        // Refresh the session with the updated member data
        Member refreshedMember = memberService.findMemberById(loginMember.getId());
        session.setAttribute(SessionConstants.LOGIN_MEMBER, refreshedMember);

        return "redirect:/api/portfolio";
    }



    @GetMapping("/member/check-email") // 이메일 중복검사
    @ResponseBody
    public boolean checkEmail(@RequestParam("email") String email) {
        return memberService.isEmailDuplicate(email);
    }
}



