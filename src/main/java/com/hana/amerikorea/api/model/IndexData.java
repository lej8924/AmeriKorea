package com.hana.amerikorea.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IndexData {
    private String rt_cd;
    private String msg_cd;
    private String msg1;
    private Object output1;
    private Object[] output2;
}