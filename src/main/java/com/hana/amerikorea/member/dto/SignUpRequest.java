package com.hana.amerikorea.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDate;

@Setter
@Getter
@ToString
@Data
public class SignUpRequest {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "Gender is required")
    private Boolean gender;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Password check is required")
    private String passwordCheck;

    @Past
    @NotNull(message = "Birthday is required")
    private LocalDate birthday;

    public SignUpRequest() {}

    public SignUpRequest(String name, Boolean gender, String email, String password, String passwordCheck, LocalDate birthday) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.birthday = birthday;
    }
}
