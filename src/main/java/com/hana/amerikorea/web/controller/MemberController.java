package com.hana.amerikorea.web.controller;

import com.hana.amerikorea.web.dto.SignUpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @PostMapping("/sign-up")
    public SignUpRequest signUp(@RequestBody SignUpRequest signUpRequest){
        return signUpRequest;
    }
}
