package com.hana.amerikorea.member.dto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String email;
    private String name;
}