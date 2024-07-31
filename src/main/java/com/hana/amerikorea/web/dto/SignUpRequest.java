package com.hana.amerikorea.web.dto;

public record SignUpRequest(
        String id,
        String password,
        String email
) {
}
