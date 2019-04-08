package com.tcxx.serve.wechat.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeChatPayTradeTypeEnum {

    JSAPI("JSAPI"),
    NATIVE("NATIVE"),
    APP("APP")
    ;

    private String type;
}
