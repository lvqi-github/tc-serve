package com.tcxx.serve.wechat.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClickEventKeyEnum {

    /**
     * 联系客服
     */
    CONTACT_CUSTOMER_SERVICE("contact_customer_service")
    ;

    private String key;
}
