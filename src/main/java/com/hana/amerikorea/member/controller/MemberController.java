package com.hana.amerikorea.member.controller;

import com.hana.amerikorea.member.constants.SessionConstants;
import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.repository.MemberRepository;
import com.hana.amerikorea.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.System.out;

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
    @GetMapping("/profile")
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

    @PostMapping("/update")
    public String updateMemberProfile(Member updatedMember, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConstants.LOGIN_MEMBER) == null) {
            response.sendRedirect("/member/profile");
            return null;
        }

        Member loginMember = (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);
        updatedMember.setId(loginMember.getId()); // Ensure the correct member is being updated

        memberService.updateMember(updatedMember);
        session.setAttribute(SessionConstants.LOGIN_MEMBER, updatedMember); // Update the session with the new member data

        return "redirect:/member/dashboard";
    }
}


//    @PostMapping("/checkId")
//    @ResponseBody
//    public Map<String, Boolean> checkId(@RequestBody Map<String, String> request) {
//        String memId = request.get("mem_id");
//        boolean exists = memberService.checkIdExists(memId);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("exists", exists);
//        return response;
//    }



