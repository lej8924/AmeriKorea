package com.hana.amerikorea.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenInfo {
    private String access_token;
    private String token_type;
    private long expires_in;
}