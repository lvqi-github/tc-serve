package com.tcxx.serve.wechat.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ButtonTypeEnum {

    CLICK("click"),
    VIEW("view")
    ;

    private String type;
}
