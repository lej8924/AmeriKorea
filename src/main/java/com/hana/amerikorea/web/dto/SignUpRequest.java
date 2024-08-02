package com.hana.amerikorea.web.dto;

public record SignUpRequest(
        String name,
        Boolean gender,
        String email,
        String password,
        String password_check,
        int birthday
) {
    /*
    name : Adam
gender: man “bool”
phone_number : 010-2421-1241 “int”
email : dfdddda@gmail.com
password : password1
password_check : password1
birthdate: 2000-01-01 “int” }
     */
}

