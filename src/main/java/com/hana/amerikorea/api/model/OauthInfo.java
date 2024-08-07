package com.hana.amerikorea.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OauthInfo {
    private String grant_type;
    private String appkey;
    private String appsecret;

}