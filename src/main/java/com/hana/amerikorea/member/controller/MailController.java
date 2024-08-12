package com.hana.amerikorea.member.controller;

import com.hana.amerikorea.member.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/member/checkEmailandName")
    @ResponseBody
    public boolean checkEmailandName(@RequestParam("email") String email, @RequestParam("name") String name) {
        return mailService.checkEmailandName(email, name);
    }

    @PostMapping("/member/sendPwd")
    public String sendPwd(@RequestParam("email") String email) {

        String tmpPassword = mailService.getTmpPassword(); //임시 비밀번호 생성

        mailService.updatePassword(tmpPassword,email); //임시 비번 저장

        mailService.sendEmail(tmpPassword,email);


        return "redirect:/member/sign-in";


    }

}
