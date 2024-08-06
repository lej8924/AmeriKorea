package com.hana.amerikorea.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiInfo {
    private String url;
    private String trId;

    public ApiInfo(String url, String trId) {
        this.url=url;
        this.trId=trId;
    }

}
