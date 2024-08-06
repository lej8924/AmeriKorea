package com.hana.amerikorea.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class SignUpRequest {
    private String name;
    private Boolean gender;
    private String email;
    private String password;
    private String passwordCheck;
    private LocalDate birthday;

    //public SignUpRequest() {}

    public SignUpRequest(String name, Boolean gender, String email, String password, String passwordCheck, LocalDate birthday) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.birthday = birthday;
    }



}
