package com.hana.amerikorea.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenInfo {
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    private String token_type;
    private long expires_in;
}