package com.hana.amerikorea.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ApiInfo {
    private String url;
    private String trId;
    private Map<String, String> defaultQueryParams;

    public ApiInfo() {

    }
    public ApiInfo(String url, String trId, Map<String, String> defaultQueryParams) {
        this.url=url;
        this.trId=trId;
        this.defaultQueryParams = defaultQueryParams;
    }


}
