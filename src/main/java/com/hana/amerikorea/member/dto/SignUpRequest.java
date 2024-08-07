package com.hana.amerikorea.member.dto;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
//@Setter
@Data
public class SignUpRequest {
    @NotNull
    private String name;

    @NotNull
    private Boolean gender;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String passwordCheck;

    @NotNull
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


    public SignUpRequest() {

    }
}
