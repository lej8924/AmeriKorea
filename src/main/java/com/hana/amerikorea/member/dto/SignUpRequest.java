package com.hana.amerikorea.member.dto;

public record SignUpRequest(
        String id,
        String password,
        String email
) {
}
