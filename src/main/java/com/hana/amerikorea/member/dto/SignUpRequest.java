package com.hana.amerikorea.member.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDate;

@Getter
@ToString
@Setter
@Data
public class SignUpRequest {

    @NotBlank
    private String name;

    @NotNull
    private Boolean gender;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    @Size(min = 8)
    private String passwordCheck;

    @NotNull
    @Past
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
