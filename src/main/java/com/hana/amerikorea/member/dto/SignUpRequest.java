package com.hana.amerikorea.member.dto;

import lombok.Getter;
import lombok.Setter;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String name;
    private Boolean gender;
    private String email;
    private String password;
    private String passwordCheck;
    private int birthday;

    public SignUpRequest(String name, Boolean gender, String email, String password, String passwordCheck, int birthday) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.birthday = birthday;
    }
}


